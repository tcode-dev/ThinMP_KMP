import MediaPlayer
import ComposeApp

class MusicPlayerImpl: ComposeApp.MusicPlayer {
    private let PREV_SECOND: Double = 3
    private let player: MPMusicPlayerController
    private let playerConfig: PlayerConfig
    private var listeners: [MusicPlayerListener] = []
    private var playingList: [SongModel] = []
    private var currentRepeat: RepeatState = .off
    private var currentShuffle: Bool = false

    init() {
        playerConfig = PlayerConfig()
        player = MPMusicPlayerController.applicationMusicPlayer
        player.repeatMode = playerConfig.getRepeat()
        player.shuffleMode = playerConfig.getShuffle()
        currentRepeat = Self.toRepeatState(mode: player.repeatMode)
        currentShuffle = player.shuffleMode == .songs
        addObserver()
        player.beginGeneratingPlaybackNotifications()
    }

    func start(songs: [SongModel], index: Int32) {
        if player.playbackState == .playing {
            player.stop()
        }

        playingList = songs

        let query = MPMediaQuery.songs()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(value: false, forProperty: MPMediaItemPropertyIsCloudItem)
        )

        guard let allItems = query.items else { return }

        let songIds = songs.map { $0.id }
        let mediaItems = songIds.compactMap { songId in
            allItems.first { String($0.persistentID) == songId }
        }

        guard !mediaItems.isEmpty else { return }

        let collection = MPMediaItemCollection(items: mediaItems)
        let descriptor = MPMusicPlayerMediaItemQueueDescriptor(itemCollection: collection)

        if Int(index) < mediaItems.count {
            descriptor.startItem = mediaItems[Int(index)]
        }

        player.setQueue(with: descriptor)
        play()
    }

    func play() {
        player.play()
    }

    func pause() {
        player.pause()
    }

    func prev() {
        if player.currentPlaybackTime <= PREV_SECOND {
            player.skipToPreviousItem()
        } else {
            player.skipToBeginning()
        }
    }

    func next() {
        player.skipToNextItem()
    }

    func seekTo(ms: Int64) {
        player.currentPlaybackTime = Double(ms) / 1000.0
    }

    func isPlaying() -> Bool {
        return player.playbackState == .playing
    }

    func getCurrentSong() -> SongModel? {
        guard let nowPlaying = player.nowPlayingItem else { return nil }
        let persistentId = String(nowPlaying.persistentID)

        return playingList.first { $0.id == persistentId }
    }

    func getCurrentPosition() -> Int64 {
        return Int64(player.currentPlaybackTime * 1000)
    }

    func getRepeat() -> RepeatState {
        return currentRepeat
    }

    func changeRepeat() {
        player.repeatMode = player.repeatMode == .none ? .all
            : player.repeatMode == .all ? .one
            : .none
        currentRepeat = Self.toRepeatState(mode: player.repeatMode)
        playerConfig.setRepeat(value: player.repeatMode)
        onChange()
    }

    func getShuffle() -> Bool {
        return currentShuffle
    }

    func changeShuffle() {
        player.shuffleMode = player.shuffleMode == .off ? .songs : .off
        currentShuffle = player.shuffleMode == .songs
        playerConfig.setShuffle(value: player.shuffleMode)
        onChange()
    }

    func addEventListener(listener: MusicPlayerListener) {
        listeners.append(listener)
    }

    func removeEventListener(listener: MusicPlayerListener) {
        listeners.removeAll { $0 === listener }
    }

    private func onChange() {
        listeners.forEach { $0.onChange() }
    }

    private func addObserver() {
        NotificationCenter.default.addObserver(
            forName: .MPMusicPlayerControllerNowPlayingItemDidChange,
            object: player,
            queue: .main
        ) { [weak self] _ in
            self?.onChange()
        }

        NotificationCenter.default.addObserver(
            forName: .MPMusicPlayerControllerPlaybackStateDidChange,
            object: player,
            queue: .main
        ) { [weak self] _ in
            self?.onChange()
        }
    }

    private static func toRepeatState(mode: MPMusicRepeatMode) -> RepeatState {
        switch mode {
        case .one: return .one
        case .all: return .all
        default: return .off
        }
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
        player.endGeneratingPlaybackNotifications()
    }
}
