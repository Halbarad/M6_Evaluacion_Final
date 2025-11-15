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
class ProductoViewModelCrearTest {

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
    fun crearProducto_exito_actualizaListaYFlag() = runTest {
        // Lista inicial vacía (init llama obtenerProductos -> retorna vacía)
        val inicial = viewModel.productos.getOrAwaitValue()
        assertEquals(0, inicial.size)

        viewModel.crearProducto(id = 1, nombre = "Test", descripcion = "Desc", precio = 10, cantidad = 2)
        val creacion = viewModel.creacionExitosa.getOrAwaitValue()
        assertTrue(creacion == true)

        val after = viewModel.productos.getOrAwaitValue()
        assertEquals(1, after.size)
        assertEquals("Test", after.first().nombre)
    }
}
