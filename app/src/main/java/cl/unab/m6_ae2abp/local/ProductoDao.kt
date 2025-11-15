package cl.unab.m6_ae2abp.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cl.unab.m6_ae2abp.modelo.ProductoRoom

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: ProductoRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productos: List<ProductoRoom>)

    @Update
    suspend fun update(producto: ProductoRoom)

    @Delete
    suspend fun delete(producto: ProductoRoom)

    @Query("SELECT * FROM productos ORDER BY id DESC")
    fun findAll(): LiveData<List<ProductoRoom>>

    @Query("DELETE FROM productos")
    suspend fun deleteAll()
}