import MediaPlayer
import ComposeApp

class AlbumRepositoryImpl: AlbumRepository {
    func findAll() -> [AlbumModel] {
        let query = MPMediaQuery.albums()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(
                value: false,
                forProperty: MPMediaItemPropertyIsCloudItem
            )
        )

        guard let collections = query.collections else { return [] }

        return collections.compactMap { collection in
            guard let mediaItem = collection.representativeItem else { return nil }
            return AlbumModel(
                id: String(mediaItem.albumPersistentID),
                name: mediaItem.albumTitle ?? "",
                artistName: mediaItem.artist ?? "",
                imageId: String(mediaItem.persistentID)
            )
        }
    }

    func findById(id: String) -> AlbumModel? {
        let query = MPMediaQuery.albums()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(
                value: UInt64(id) ?? 0,
                forProperty: MPMediaItemPropertyAlbumPersistentID
            )
        )

        guard let collections = query.collections else { return nil }
        guard let collection = collections.first else { return nil }
        guard let mediaItem = collection.representativeItem else { return nil }

        return AlbumModel(
            id: String(mediaItem.albumPersistentID),
            name: mediaItem.albumTitle ?? "",
            artistName: mediaItem.artist ?? "",
            imageId: String(mediaItem.persistentID)
        )
    }
}
