package pw.cub3d.lemmy.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pw.cub3d.lemmy.core.data.AuthRepository
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordVerify = MutableLiveData<String>()

    fun onRegister() {
        if (password.value!! == passwordVerify.value!!) {
            authRepository.register(
                username.value!!,
                email.value,
                password.value!!,
                passwordVerify.value!!
            )
        }
    }

    fun getAuthState() = authRepository.getLoginState()
}

