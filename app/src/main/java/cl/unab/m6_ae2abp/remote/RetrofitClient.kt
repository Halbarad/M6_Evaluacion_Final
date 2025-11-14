package cl.unab.m6_ae2abp.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://evaluacion-6-341597259134.europe-west1.run.app/"

    val api: ProductoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoService::class.java)
    }

}