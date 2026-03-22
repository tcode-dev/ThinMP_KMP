import MediaPlayer
import UIKit

class ArtworkRepository {
    func getArtwork(id: String) -> Data? {
        let persistentID = UInt64(id)
        let property = MPMediaPropertyPredicate(value: false, forProperty: MPMediaItemPropertyIsCloudItem)
        let query = MPMediaQuery.songs()

        query.addFilterPredicate(property)

        guard let media = query.collections?.first(where: { persistentID == $0.representativeItem?.persistentID }) else {
            return nil
        }

        let image = media.representativeItem?.artwork?.image(at: CGSize(width: 100, height: 100))

        return image?.pngData()
    }
}
