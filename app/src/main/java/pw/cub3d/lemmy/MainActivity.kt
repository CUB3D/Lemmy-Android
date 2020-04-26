package pw.cub3d.lemmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.databinding.ActivityMainBinding
import pw.cub3d.lemmy.ui.postListView.PostViewFragmentDirections
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nc = findNavController(R.id.main_fragmentHost)

        binding.mainBottomNavigation.setOnNavigationItemReselectedListener {
            println("Nav item selected: $it")
            when (it.itemId) {
                R.id.bottomNavMenu_home -> nc.navigate(R.id.navGraph_postListViewFragment)
                R.id.bottomNavMenu_profile -> nc.navigate(R.id.navGraph_profileFragment)
            }
        }

        if(authRepository.isLoggedIn()) {
            nc.navigate(R.id.action_loginFragment_to_postListViewFragment)
        }
    }
}
