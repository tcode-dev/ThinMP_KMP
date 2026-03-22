import MediaPlayer
import ComposeApp

class SongRepository {
    func findAll() -> [SongModel] {
        let query = MPMediaQuery.songs()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(
                value: false,
                forProperty: MPMediaItemPropertyIsCloudItem
            )
        )

        guard let collections = query.collections else { return [] }

        return collections.compactMap { collection in
            guard let mediaItem = collection.representativeItem else { return nil }
            return SongModel(
                id: String(mediaItem.persistentID),
                name: mediaItem.title ?? "",
                artistName: mediaItem.artist ?? "",
                albumId: String(mediaItem.albumPersistentID),
                albumName: mediaItem.albumTitle ?? "",
                duration: Int32(mediaItem.playbackDuration),
                imageId: String(mediaItem.persistentID)
            )
        }
    }

    func findById(id: String) -> SongModel? {
        let query = MPMediaQuery.songs()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(
                value: false,
                forProperty: MPMediaItemPropertyIsCloudItem
            )
        )

        guard let collections = query.collections else { return nil }

        guard let collection = collections.first(where: {
            $0.representativeItem?.persistentID.description == id
        }) else { return nil }

        guard let mediaItem = collection.representativeItem else { return nil }

        return SongModel(
            id: String(mediaItem.persistentID),
            name: mediaItem.title ?? "",
            artistName: mediaItem.artist ?? "",
            albumId: String(mediaItem.albumPersistentID),
            albumName: mediaItem.albumTitle ?? "",
            duration: Int32(mediaItem.playbackDuration),
            imageId: String(mediaItem.persistentID)
        )
    }
}
