import ComposeApp

class SongBridgeImpl: SongBridge {
    private let songRepository = SongRepository()

    func findAll() -> [SongModel] {
        return songRepository.findAll()
    }

    func findById(id: String) -> SongModel? {
        return songRepository.findById(id: id)
    }
}
