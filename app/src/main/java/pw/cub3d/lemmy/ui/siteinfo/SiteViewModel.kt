package pw.cub3d.lemmy.ui.siteinfo

import androidx.lifecycle.ViewModel
import pw.cub3d.lemmy.core.data.SiteRepository
import javax.inject.Inject

class SiteViewModel @Inject constructor(
    private val siteRepository: SiteRepository
): ViewModel() {
    fun getSite() = siteRepository.getSiteInfo()
}
