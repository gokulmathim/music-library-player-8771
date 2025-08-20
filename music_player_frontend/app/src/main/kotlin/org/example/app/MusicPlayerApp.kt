package org.example.app

import android.app.Application
import org.example.app.data.MusicRepository
import org.example.app.player.PlayerManager

/**
 * Application class for initializing app-wide singletons like repository and player manager.
 */
class MusicPlayerApp : Application() {

    lateinit var repository: MusicRepository
        private set

    lateinit var playerManager: PlayerManager
        private set

    override fun onCreate() {
        super.onCreate()
        repository = MusicRepository(this)
        playerManager = PlayerManager(this, repository)
    }
}
