package dev.tcode.thinmpk.config

import platform.Foundation.NSHomeDirectory

internal actual fun getDataStorePath(): String {
    return NSHomeDirectory() + "/Documents/" + DATA_STORE_FILE_NAME
}
