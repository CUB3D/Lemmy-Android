package pw.cub3d.lemmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import pw.cub3d.lemmy.core.data.AuthRepository
import pw.cub3d.lemmy.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(authRepository.isLoggedIn()) {
            findNavController(R.id.main_fragmentHost).navigate(R.id.action_loginFragment_to_postListViewFragment)
        }
    }
}
