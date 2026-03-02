package dev.tcode.thinmpk

import android.app.Application

class MainApplication : Application() {
    companion object {
        lateinit var appContext: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
