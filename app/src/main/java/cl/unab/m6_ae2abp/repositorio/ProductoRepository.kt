package cl.unab.m6_ae2abp.repositorio

import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.remote.ProductoService
import cl.unab.m6_ae2abp.remote.RetrofitClient

class ProductoRepository(
    private val api: ProductoService = RetrofitClient.api
) {
    private val user = "grupo1"

    suspend fun leerProductos(): List<Producto> = api.leerProductos(user)

    suspend fun leerProductoPorId(id: Int) = api.leerProductoPorId(user, id)

    suspend fun eliminarProducto(id: Int) = api.eliminarProducto(user, id)

    suspend fun actualizarProducto(id: Int, producto: Producto) = api.actualizarProducto(user, id, producto)

    suspend fun crearProducto(producto: Producto) = api.crearProducto(user, producto)

    suspend fun obtenerUltimoId() = api.obtenerUltimoId(user)
}