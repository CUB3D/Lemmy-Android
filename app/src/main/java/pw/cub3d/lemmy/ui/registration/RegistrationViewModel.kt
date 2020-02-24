package pw.cub3d.lemmy.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel() {
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordVerify = MutableLiveData<String>()

    fun onRegister() {
    }
}

