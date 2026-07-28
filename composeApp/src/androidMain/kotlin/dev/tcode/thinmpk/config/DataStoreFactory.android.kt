package dev.tcode.thinmpk.config

import dev.tcode.thinmpk.MainApplication

internal actual fun getDataStorePath(): String {
    return MainApplication.appContext.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
}
