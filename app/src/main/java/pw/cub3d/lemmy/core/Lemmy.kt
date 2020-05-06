package pw.cub3d.lemmy.core

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import pw.cub3d.lemmy.core.dagger.DaggerComponentProvider
import pw.cub3d.lemmy.core.dagger.DaggerLemmyComponent
import pw.cub3d.lemmy.core.dagger.LemmyComponent
import javax.inject.Inject

class Lemmy: Application(), HasAndroidInjector, DaggerComponentProvider {

    override fun onCreate() {
        super.onCreate()

        comp = DaggerLemmyComponent.builder()
            .context(this)
            .build()

        component.inject(this)

        AndroidThreeTen.init(this)
    }

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector() = dispatchingAndroidInjector

    lateinit var comp: LemmyComponent
    override val component: LemmyComponent
        get() = comp
}