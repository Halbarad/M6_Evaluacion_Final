package cl.unab.m6_ae2abp.repositorio

import androidx.lifecycle.LiveData
import cl.unab.m6_ae2abp.local.ProductoDao
import cl.unab.m6_ae2abp.modelo.ProductoRoom

class ProductoRoomRepository(private val productoDao: ProductoDao) {

    val listadoProductos: LiveData<List<ProductoRoom>> = productoDao.findAll()

    suspend fun insert(producto: ProductoRoom) {
        productoDao.insert(producto)
    }

    suspend fun update(producto: ProductoRoom) {
        productoDao.update(producto)
    }

    suspend fun delete(producto: ProductoRoom) {
        productoDao.delete(producto)
    }

    suspend fun deleteAll() {
        productoDao.deleteAll()
    }
}