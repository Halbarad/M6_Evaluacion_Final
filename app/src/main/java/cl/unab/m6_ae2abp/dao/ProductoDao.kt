package cl.unab.m6_ae2abp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cl.unab.m6_ae2abp.modelo.Producto

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos ORDER BY id DESC")
    suspend fun getAll(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getById(id: Int): Producto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)
}
