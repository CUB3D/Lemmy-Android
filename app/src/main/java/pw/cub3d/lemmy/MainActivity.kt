package pw.cub3d.lemmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pw.cub3d.lemmy.ui.postView.PostViewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragmentHost, PostViewFragment())
            commit()
        }
    }
}
