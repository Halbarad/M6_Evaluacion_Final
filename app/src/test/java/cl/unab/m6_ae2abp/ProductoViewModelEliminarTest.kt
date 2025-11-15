package cl.unab.m6_ae2abp

import cl.unab.m6_ae2abp.repositorio.ProductoRepository
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoViewModelEliminarTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Ejecuta las actualizaciones de LiveData de forma síncrona en tests JVM
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fakeService = FakeProductoService()
    private val repository = ProductoRepository(fakeService)
    private lateinit var viewModel: ProductoViewModel

    @Before
    fun setup() {
        viewModel = ProductoViewModel(repository)
    }

    @Test
    fun eliminarProducto_exito_actualizaListaYFlag() = runTest {
        // Crear primer producto y esperar la confirmación
        viewModel.crearProducto(id = 1, nombre = "A", descripcion = "D", precio = 1, cantidad = 1)
        val creacion1 = viewModel.creacionExitosa.getOrAwaitValue()
        assertTrue(creacion1 == true)

        // Crear segundo producto y esperar la confirmación
        viewModel.crearProducto(id = 2, nombre = "B", descripcion = "D", precio = 2, cantidad = 2)
        val creacion2 = viewModel.creacionExitosa.getOrAwaitValue()
        assertTrue(creacion2 == true)

        // Obtener lista actualizada
        val productosAntes = viewModel.productos.getOrAwaitValue()
        assertEquals(2, productosAntes.size)

        // Eliminar uno y esperar confirmación
        viewModel.eliminarProducto(1)
        val eliminacionFlag = viewModel.eliminacionExitosa.getOrAwaitValue()
        assertTrue(eliminacionFlag == true)

        // Obtener lista actualizada
        val productosDespues = viewModel.productos.getOrAwaitValue()
        assertEquals(1, productosDespues.size)
        assertEquals(2, productosDespues.first().id)
    }
}
