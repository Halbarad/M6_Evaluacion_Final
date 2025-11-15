package cl.unab.m6_ae2abp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.unab.m6_ae2abp.AppDatabase
import cl.unab.m6_ae2abp.repositorio.ProductoRepository

class ProductoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            val productoDao = AppDatabase.getDatabase(context).productoDao()
            val repository = ProductoRepository(productoDao = productoDao)
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
