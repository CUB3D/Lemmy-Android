package pw.cub3d.lemmy.core.dagger

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pw.cub3d.lemmy.ui.community.CommunityViewModel
import pw.cub3d.lemmy.ui.home.HomeViewModel
import pw.cub3d.lemmy.ui.inbox.inboxTab.InboxTabViewModel
import pw.cub3d.lemmy.ui.loading.LoadingViewModel
import pw.cub3d.lemmy.ui.login.LoginViewModel
import pw.cub3d.lemmy.ui.modlog.ModlogViewModel
import pw.cub3d.lemmy.ui.postListView.PostsViewModel
import pw.cub3d.lemmy.ui.profile.ProfileViewModel
import pw.cub3d.lemmy.ui.registration.RegistrationViewModel
import pw.cub3d.lemmy.ui.search.SearchViewModel
import pw.cub3d.lemmy.ui.singlePostView.SinglePostViewModel
import pw.cub3d.lemmy.ui.siteinfo.SiteViewModel
import pw.cub3d.lemmy.ui.userSettings.UserSettingsViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class
    ]
)
interface LemmyComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(app: Context): Builder

        fun build(): LemmyComponent
    }

    fun loadingViewModel(): ViewModelFactory<LoadingViewModel>
    fun searchViewModelFactory(): ViewModelFactory<SearchViewModel>
    fun communityViewModelFactory(): ViewModelFactory<CommunityViewModel>
    fun siteInfoViewModelFactory(): ViewModelFactory<SiteViewModel>
    fun modLogViewModelFactory(): ViewModelFactory<ModlogViewModel>
    fun homeViewModelFactory(): ViewModelFactory<HomeViewModel>
    fun postsViewModelFactory(): ViewModelFactory<PostsViewModel>
    fun loginViewModelFactory(): ViewModelFactory<LoginViewModel>
    fun registrationViewModelFactory(): ViewModelFactory<RegistrationViewModel>
    fun userSettingsViewModelFactory(): ViewModelFactory<UserSettingsViewModel>
    fun singlePostViewModelFactory(): ViewModelFactory<SinglePostViewModel>
    fun profileViewModelFactory(): ViewModelFactory<ProfileViewModel>
    fun inboxTabViewModelFactory(): ViewModelFactory<InboxTabViewModel>
}