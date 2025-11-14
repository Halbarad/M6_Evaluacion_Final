package cl.unab.m6_ae2abp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.repositorio.ProductoRepository
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repository = ProductoRepository()

    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> = _productos

    private val _creacionExitosa = MutableLiveData<Boolean>()
    val creacionExitosa: LiveData<Boolean> = _creacionExitosa

    private val _eliminacionExitosa = MutableLiveData<Boolean>()
    val eliminacionExitosa: LiveData<Boolean> = _eliminacionExitosa

    private val _productoEncontrado = MutableLiveData<Producto?>()
    val productoEncontrada: LiveData<Producto?> = _productoEncontrado

    private val _actualizacionExitosa = MutableLiveData<Boolean>()
    val actualizacionExitosa: LiveData<Boolean> = _actualizacionExitosa

    init {
        obtenerProductos()
    }

    fun obtenerProductos() {
        viewModelScope.launch {
            try {
                val response = repository.leerProductos()
                _productos.postValue(response.sortedByDescending { it.id })
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error al obtener productos", e)
                _productos.postValue(emptyList())
            }
        }
    }

    fun buscarProductoPorId(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.leerProductoPorId(id)
                if (response.isSuccessful) {
                    _productoEncontrado.postValue(response.body())
                } else {
                    _productoEncontrado.postValue(null)
                }
            } catch (e: Exception) {
                _productoEncontrado.postValue(null)
            }
        }
    }

    fun crearProducto(producto: Producto) {
        viewModelScope.launch {
            try {
                val response = repository.crearProducto(producto)
                if (response.isSuccessful) {
                    _creacionExitosa.postValue(true)
                    obtenerProductos() // Refresh list on success
                } else {
                    _creacionExitosa.postValue(false)
                    Log.d("ProductoViewModel", "Error al crear producto: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _creacionExitosa.postValue(false)
                Log.e("ProductoViewModel", "Excepción al crear producto", e)
            }
        }
    }

    fun actualizarProducto(id: Int, producto: Producto) {
        viewModelScope.launch {
            try {
                val response = repository.actualizarProducto(id, producto)
                if (response.isSuccessful) {
                    _actualizacionExitosa.postValue(true)
                    obtenerProductos() // Refresh list on success
                } else {
                    _actualizacionExitosa.postValue(false)
                    Log.d("ProductoViewModel", "Error al actualizar producto: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _actualizacionExitosa.postValue(false)
                Log.e("ProductoViewModel", "Excepción al actualizar producto", e)
            }
        }
    }

    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.eliminarProducto(id)
                if (response.isSuccessful) {
                    _eliminacionExitosa.postValue(true)
                    obtenerProductos() // Refresh list on success
                } else {
                    _eliminacionExitosa.postValue(false)
                    Log.d("ProductoViewModel", "Error al eliminar producto: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _eliminacionExitosa.postValue(false)
                Log.e("ProductoViewModel", "Excepción al eliminar producto", e)
            }
        }
    }
}