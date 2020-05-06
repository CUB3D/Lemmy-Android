package pw.cub3d.lemmy.core

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import pw.cub3d.lemmy.core.dagger.DaggerComponentProvider
import pw.cub3d.lemmy.core.dagger.DaggerLemmyComponent
import pw.cub3d.lemmy.core.dagger.LemmyComponent
import timber.log.Timber

class Lemmy: Application(), DaggerComponentProvider {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        comp = DaggerLemmyComponent.builder()
            .context(this)
            .build()

        AndroidThreeTen.init(this)
    }

    lateinit var comp: LemmyComponent
    override val component: LemmyComponent
        get() = comp
}