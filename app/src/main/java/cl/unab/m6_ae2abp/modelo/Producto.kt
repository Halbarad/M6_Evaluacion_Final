package cl.unab.m6_ae2abp.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Int,
    var cantidad: Int
) : Parcelable
