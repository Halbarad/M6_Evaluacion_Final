package cl.unab.m6_ae2abp.modelo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "productos")
data class ProductoRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nombre: String,
    var descripcion: String,
    var precio: Int,
    var cantidad: Int
) : Parcelable