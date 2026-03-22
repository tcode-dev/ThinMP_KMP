import MediaPlayer
import UIKit

class ArtworkRepository {
    func getArtwork(id: String) -> Data? {
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
        guard let artwork = mediaItem.artwork else { return nil }
        guard let uiImage = artwork.image(at: CGSize(width: 100, height: 100)) else { return nil }

        return uiImage.pngData()
    }
}
