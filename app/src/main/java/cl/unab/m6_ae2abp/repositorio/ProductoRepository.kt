package cl.unab.m6_ae2abp.repositorio

import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.remote.ProductoService
import cl.unab.m6_ae2abp.remote.RetrofitClient

class ProductoRepository(
    private val api: ProductoService = RetrofitClient.api
) {

    suspend fun leerProductos(): List<Producto> = api.leerProductos()

    suspend fun leerProductoPorId(id: Int) = api.leerProductoPorId(id)

    suspend fun eliminarProducto(id: Int) = api.eliminarProducto(id)

    suspend fun actualizarProducto(id: Int, producto: Producto) = api.actualizarProducto(id, producto)

    suspend fun crearProducto(producto: Producto) = api.crearProducto(producto)

}