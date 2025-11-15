package cl.unab.m6_ae2abp.repositorio

import cl.unab.m6_ae2abp.dao.ProductoDao
import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.remote.ProductoService
import cl.unab.m6_ae2abp.remote.RetrofitClient

class ProductoRepository(
    private val api: ProductoService = RetrofitClient.api,
    private val productoDao: ProductoDao
) {
    private val user = "grupo1"

    // Funciones para la API Remota (Retrofit)
    suspend fun leerProductos() = api.leerProductos(user)

    suspend fun leerProductoPorId(id: Int) = api.leerProductoPorId(user, id)

    suspend fun eliminarProducto(id: Int) = api.eliminarProducto(user, id)

    suspend fun actualizarProducto(id: Int, producto: Producto) = api.actualizarProducto(user, id, producto)

    suspend fun crearProducto(producto: Producto) = api.crearProducto(user, producto)

    suspend fun obtenerUltimoId() = api.obtenerUltimoId(user)

    // Funciones para la Base de Datos Local (Room)
    suspend fun obtenerProductosDesdeDb(): List<Producto> = productoDao.getAll()

    suspend fun obtenerProductoPorIdDesdeDb(id: Int): Producto? = productoDao.getById(id)

    suspend fun insertarProductoEnDb(producto: Producto) = productoDao.insert(producto)

    suspend fun actualizarProductoEnDb(producto: Producto) = productoDao.update(producto)

    suspend fun eliminarProductoEnDb(producto: Producto) = productoDao.delete(producto)
}
