package pw.cub3d.lemmy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import pw.cub3d.lemmy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
