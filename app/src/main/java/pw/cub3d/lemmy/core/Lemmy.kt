package pw.cub3d.lemmy.core

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import pw.cub3d.lemmy.core.dagger.DaggerLemmyComponent
import javax.inject.Inject

class Lemmy: Application(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()

        DaggerLemmyComponent.builder()
            .context(this)
            .build()
            .inject(this)

        AndroidThreeTen.init(this)
    }

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector() = dispatchingAndroidInjector
}