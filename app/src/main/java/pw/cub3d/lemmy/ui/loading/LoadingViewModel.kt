package pw.cub3d.lemmy.ui.loading

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.AuthRepository
import javax.inject.Inject

class LoadingViewModel @Inject constructor(
    public val authRepository: AuthRepository
): ViewModel()