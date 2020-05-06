package pw.cub3d.lemmy.core.dagger

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import pw.cub3d.lemmy.core.Lemmy

interface DaggerComponentProvider {
    val component: LemmyComponent
}

val Activity.injector get() = (application as DaggerComponentProvider).component
fun Activity.inject() = AndroidInjection.inject(this)// injector.inject(application as Lemmy)

val Fragment.injector get() = (requireActivity().application as DaggerComponentProvider).component
fun Fragment.inject() = AndroidSupportInjection.inject(this)//injector.inject(requireActivity().application as Lemmy)