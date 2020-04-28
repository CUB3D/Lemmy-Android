package pw.cub3d.lemmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.databinding.ActivityMainBinding
import pw.cub3d.lemmy.ui.loading.LoadingFragmentDirections
import pw.cub3d.lemmy.ui.postListView.PostViewFragmentDirections
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nc = findNavController(R.id.main_fragmentHost)

        binding.mainBottomNavigation.setOnNavigationItemReselectedListener {
            println("Nav item selected: $it")
            when (it.itemId) {
                R.id.bottomNavMenu_home -> nc.navigate(R.id.homeFragment)
                R.id.bottomNavMenu_profile -> nc.navigate(R.id.navGraph_profileFragment)
            }
        }

        nc.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id in listOf(R.id.loginFragment, R.id.registrationFragment)) {
                binding.mainBottomNavigation.visibility = View.GONE
            } else {
                binding.mainBottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}
