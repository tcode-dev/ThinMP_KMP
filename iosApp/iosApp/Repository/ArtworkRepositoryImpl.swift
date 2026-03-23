import ComposeApp
import MediaPlayer
import UIKit

class ArtworkRepositoryImpl: ArtworkRepository {
    func getArtwork(id: String) -> KotlinByteArray? {
        guard let persistentID = UInt64(id) else { return nil }

        let query = MPMediaQuery.songs()

        query.addFilterPredicate(MPMediaPropertyPredicate(value: false, forProperty: MPMediaItemPropertyIsCloudItem))
        query.addFilterPredicate(MPMediaPropertyPredicate(value: persistentID, forProperty: MPMediaItemPropertyPersistentID))

        guard let item = query.items?.first else { return nil }
        guard let image = item.artwork?.image(at: CGSize(width: 100, height: 100)) else { return nil }
        guard let data = image.pngData() else { return nil }
        let bytes = [UInt8](data)
        let kotlinByteArray = KotlinByteArray(size: Int32(bytes.count))

        for (index, byte) in bytes.enumerated() {
            kotlinByteArray.set(index: Int32(index), value: Int8(bitPattern: byte))
        }

        return kotlinByteArray
    }
}
