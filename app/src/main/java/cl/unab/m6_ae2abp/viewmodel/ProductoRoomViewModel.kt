package cl.unab.m6_ae2abp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cl.unab.m6_ae2abp.local.ProductoDatabase
import cl.unab.m6_ae2abp.modelo.ProductoRoom
import cl.unab.m6_ae2abp.repositorio.ProductoRoomRepository
import kotlinx.coroutines.launch

class ProductoRoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductoRoomRepository

    val listadoProductos: LiveData<List<ProductoRoom>>

    init {
        val productoDao = ProductoDatabase.getDatabase(application).productoDao()
        repository = ProductoRoomRepository(productoDao)
        listadoProductos = repository.listadoProductos
    }

    fun insert(producto: ProductoRoom) = viewModelScope.launch {
        repository.insert(producto)
    }

    fun update(producto: ProductoRoom) = viewModelScope.launch {
        repository.update(producto)
    }

    fun delete(producto: ProductoRoom) = viewModelScope.launch {
        repository.delete(producto)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}