package pw.cub3d.lemmy.core.dagger

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import pw.cub3d.lemmy.core.Lemmy
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApiModule::class,
        ScreenBindingModule::class
    ]
)
interface LemmyComponent: AndroidInjector<Lemmy> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(app: Context): Builder

        fun build(): LemmyComponent
    }
}