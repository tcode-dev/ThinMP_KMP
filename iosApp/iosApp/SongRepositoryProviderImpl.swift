import ComposeApp
import MediaPlayer

class SongRepositoryProviderImpl: SongRepositoryProvider {
    func findAll() -> [SongModel] {
        let query = MPMediaQuery.songs()
        let predicate = MPMediaPropertyPredicate(
            value: false,
            forProperty: MPMediaItemPropertyIsCloudItem
        )
        query.addFilterPredicate(predicate)

        guard let items = query.items else { return [] }

        return items.map { item in
            SongModel(
                id: String(item.persistentID),
                name: item.title ?? "",
                artistName: item.artist ?? "",
                albumId: String(item.albumPersistentID),
                albumName: item.albumTitle ?? "",
                duration: Int32(item.playbackDuration)
            )
        }
    }
}
