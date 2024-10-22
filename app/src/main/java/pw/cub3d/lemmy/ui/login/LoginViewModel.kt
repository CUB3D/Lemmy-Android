package pw.cub3d.lemmy.ui.login

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.AuthRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    fun login(username: String, password: String) {
        authRepository.login(username, password)
    }

    fun getAuthState() = authRepository.getLoginState()
}