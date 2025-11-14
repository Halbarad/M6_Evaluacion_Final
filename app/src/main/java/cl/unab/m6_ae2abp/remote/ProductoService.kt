package cl.unab.m6_ae2abp.remote

import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.modelo.SiguienteId
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoService {

    @GET("{user}/productos")
    suspend fun leerProductos(
        @Path("user") user: String
    ): List<Producto>

    @GET("{user}/producto/{id}")
    suspend fun leerProductoPorId(
        @Path("user") user: String,
        @Path("id") id: Int
    ): Response<Producto>

    @PUT("{user}/productos/{id}")
    suspend fun actualizarProducto(
        @Path("user") user: String,
        @Path("id") id: Int,
        @Body producto: Producto
    ): Response<Producto>

    @DELETE("{user}/productos/{id}")
    suspend fun eliminarProducto(
        @Path("user") user: String,
        @Path("id") id: Int
    ): Response<Unit>

    @POST("{user}/productos")
    suspend fun crearProducto(
        @Path("user") user: String,
        @Body producto: Producto
    ): Response<Producto>

    @GET("{user}/productos/lastid")
    suspend fun obtenerUltimoId(
        @Path("user") user: String
    ): Response<SiguienteId>

}
