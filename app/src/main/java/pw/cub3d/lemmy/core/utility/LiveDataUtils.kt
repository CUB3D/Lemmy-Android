package pw.cub3d.lemmy.core.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T,X> LiveData<T>.map(mapFunc: (T)->X) = Transformations.map(this, mapFunc)