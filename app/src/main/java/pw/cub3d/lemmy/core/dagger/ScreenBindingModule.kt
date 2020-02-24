package pw.cub3d.lemmy.core.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pw.cub3d.lemmy.ui.postView.PostViewFragment

@Module
abstract class ScreenBindingModule {
    @ContributesAndroidInjector
    abstract fun postsFragment(): PostViewFragment
}