package pw.cub3d.lemmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import pw.cub3d.lemmy.core.dagger.inject
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.databinding.ActivityMainBinding
import pw.cub3d.lemmy.ui.loading.LoadingFragmentDirections
import pw.cub3d.lemmy.ui.postListView.PostViewFragmentDirections
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
