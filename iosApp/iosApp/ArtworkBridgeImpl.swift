import ComposeApp

class ArtworkBridgeImpl: ArtworkBridge {
    private let artworkRepository = ArtworkRepository()

    func getArtwork(id: String) -> KotlinByteArray? {
        guard let data = artworkRepository.getArtwork(id: id) else { return nil }

        let bytes = [UInt8](data)
        let kotlinByteArray = KotlinByteArray(size: Int32(bytes.count))
        for (index, byte) in bytes.enumerated() {
            kotlinByteArray.set(index: Int32(index), value: Int8(bitPattern: byte))
        }

        return kotlinByteArray
    }
}
