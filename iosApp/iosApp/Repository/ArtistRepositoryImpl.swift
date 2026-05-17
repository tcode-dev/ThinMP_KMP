import MediaPlayer
import ComposeApp

class ArtistRepositoryImpl: ArtistRepository {
    func findAll() -> [ArtistModel] {
        let query = MPMediaQuery.artists()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(
                value: false,
                forProperty: MPMediaItemPropertyIsCloudItem
            )
        )

        guard let collections = query.collections else { return [] }

        return collections.compactMap { collection in
            guard let mediaItem = collection.representativeItem else { return nil }
            return ArtistModel(
                id: String(mediaItem.artistPersistentID),
                name: mediaItem.artist ?? "",
                imageId: String(mediaItem.artistPersistentID)
            )
        }
    }

    func findById(id: String) -> ArtistModel? {
        let query = MPMediaQuery.artists()
        query.addFilterPredicate(
            MPMediaPropertyPredicate(
                value: UInt64(id) ?? 0,
                forProperty: MPMediaItemPropertyArtistPersistentID
            )
        )

        guard let collections = query.collections else { return nil }
        guard let collection = collections.first else { return nil }
        guard let mediaItem = collection.representativeItem else { return nil }

        return ArtistModel(
            id: String(mediaItem.artistPersistentID),
            name: mediaItem.artist ?? "",
            imageId: String(mediaItem.artistPersistentID)
        )
    }
}
