package pw.cub3d.lemmy.core.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pw.cub3d.lemmy.MainActivity
import pw.cub3d.lemmy.ui.home.HomeFragment
import pw.cub3d.lemmy.ui.home.HomeViewModel
import pw.cub3d.lemmy.ui.loading.LoadingFragment
import pw.cub3d.lemmy.ui.login.LoginFragment
import pw.cub3d.lemmy.ui.login.LoginViewModel
import pw.cub3d.lemmy.ui.postListView.PostViewFragment
import pw.cub3d.lemmy.ui.postListView.PostsViewModel
import pw.cub3d.lemmy.ui.profile.ProfileFragment
import pw.cub3d.lemmy.ui.profile.ProfileViewModel
import pw.cub3d.lemmy.ui.profile.comments.StaticMixedPostsCommentsFragment
import pw.cub3d.lemmy.ui.registration.RegistrationFragment
import pw.cub3d.lemmy.ui.registration.RegistrationViewModel
import pw.cub3d.lemmy.ui.singlePostView.SinglePostFragment
import pw.cub3d.lemmy.ui.singlePostView.SinglePostViewModel

@Module
abstract class ScreenBindingModule {
    @ContributesAndroidInjector
    abstract fun loadingFragment(): LoadingFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun postsFragment(): PostViewFragment

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    internal abstract fun bindPostsViewModel(vm: PostsViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(vm: LoginViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun registrationFragment(): RegistrationFragment
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    internal abstract fun bindRegistrationViewModel(vm: RegistrationViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun singlePostFragment(): SinglePostFragment

    @Binds
    @IntoMap
    @ViewModelKey(SinglePostViewModel::class)
    internal abstract fun bindSinglePostViewModel(vm: SinglePostViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun bindProfileViewModel(vm: ProfileViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun staticMixedPostsCommentsFragment(): StaticMixedPostsCommentsFragment


    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}