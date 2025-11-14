package cl.unab.m6_ae2abp.remote

import cl.unab.m6_ae2abp.modelo.Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoService {

    @GET("grupo_1/productos")
    suspend fun leerProductos(): List<Producto>

    @GET("grupo_1/producto/{id}")
    suspend fun leerProductoPorId(@Path("id") id: Int): Response<Producto>

    @DELETE("grupo_1/productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Int): Response<Unit>

    @PUT("grupo_1/productos/{id}")
    suspend fun actualizarProducto(@Path("id") id: Int, @Body producto: Producto): Response<Producto>

    @POST("grupo_1/productos")
    suspend fun crearProducto(@Body producto: Producto): Response<Producto>
}