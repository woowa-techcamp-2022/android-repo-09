package co.kr.woowahan_repo.application

import android.app.Application
import co.kr.woowahan_repo.BuildConfig
import timber.log.Timber

class WoowahanRepoApplication: Application() {

    val appContainer = AppContainer()

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}