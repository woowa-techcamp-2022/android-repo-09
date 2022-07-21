package co.kr.woowahan_repo.application

import android.app.Application
import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.domain.repository.*
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WoowahanRepoApplication: Application() {
    companion object {
        lateinit var instance: WoowahanRepoApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}