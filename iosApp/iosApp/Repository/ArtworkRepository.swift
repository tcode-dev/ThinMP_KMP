import MediaPlayer
import UIKit

class ArtworkRepository {
    func getArtwork(id: String) -> Data? {
        guard let persistentID = UInt64(id) else { return nil }

        let query = MPMediaQuery.songs()

        query.addFilterPredicate(MPMediaPropertyPredicate(value: false, forProperty: MPMediaItemPropertyIsCloudItem))
        query.addFilterPredicate(MPMediaPropertyPredicate(value: persistentID, forProperty: MPMediaItemPropertyPersistentID))

        guard let item = query.items?.first else { return nil }

        let image = item.artwork?.image(at: CGSize(width: 100, height: 100))

        return image?.pngData()
    }
}
