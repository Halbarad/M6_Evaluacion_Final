package cl.unab.m6_ae2abp

import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.modelo.SiguienteId
import cl.unab.m6_ae2abp.remote.ProductoService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeProductoService : ProductoService {
    private val productos = mutableListOf<Producto>()
    private var lastId = 0

    override suspend fun leerProductos(user: String): List<Producto> = productos.toList()

    override suspend fun leerProductoPorId(user: String, id: Int): Response<Producto> {
        val p = productos.find { it.id == id }
        return if (p != null) Response.success(p) else Response.error(404, "".toResponseBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun actualizarProducto(user: String, id: Int, producto: Producto): Response<Producto> {
        val idx = productos.indexOfFirst { it.id == id }
        return if (idx >= 0) {
            productos[idx] = producto
            Response.success(producto)
        } else Response.error(404, "".toResponseBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun eliminarProducto(user: String, id: Int): Response<Unit> {
        val removed = productos.removeIf { it.id == id }
        return if (removed) Response.success(Unit) else Response.error(404, "".toResponseBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun crearProducto(user: String, producto: Producto): Response<Producto> {
        productos.add(producto)
        if (producto.id > lastId) lastId = producto.id
        return Response.success(producto)
    }

    override suspend fun obtenerUltimoId(user: String): Response<SiguienteId> {
        return Response.success(SiguienteId(last_id = lastId, next_id = lastId + 1))
    }
}
