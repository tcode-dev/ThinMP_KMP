package dev.tcode.thinmpk

import android.app.Application
import dev.tcode.thinmpk.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    companion object {
        lateinit var appContext: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin {
            androidContext(this@MainApplication)
            modules(androidModule)
        }
    }
}
