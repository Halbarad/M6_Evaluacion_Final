package cl.unab.m6_ae2abp.repositorio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import cl.unab.m6_ae2abp.local.ProductoDao
import cl.unab.m6_ae2abp.modelo.ProductoRoom
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

// Un DAO falso simple para tests del repositorio
class FakeProductoDao : ProductoDao {
    private val list = mutableListOf<ProductoRoom>()
    private val live = MutableLiveData<List<ProductoRoom>>(list)

    override suspend fun insert(producto: ProductoRoom) {
        val toInsert = producto.copy(id = (list.maxOfOrNull { it.id } ?: 0) + 1)
        list.add(0, toInsert) // mantener orden DESC como la query
        live.value = list.toList()
    }

    override suspend fun insert(productos: List<ProductoRoom>) {
        productos.forEach { insert(it) }
    }

    override suspend fun update(producto: ProductoRoom) {
        val idx = list.indexOfFirst { it.id == producto.id }
        if (idx >= 0) list[idx] = producto
        live.value = list.toList()
    }

    override suspend fun delete(producto: ProductoRoom) {
        list.removeAll { it.id == producto.id }
        live.value = list.toList()
    }

    override fun findAll() = live

    override suspend fun deleteAll() {
        list.clear()
        live.value = list.toList()
    }
}

class ProductoRoomRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun repository_insert_updatesList() {
        runBlocking {
            val fakeDao = FakeProductoDao()
            val repo = ProductoRoomRepository(fakeDao)

            val p = ProductoRoom(nombre = "RepoTest", descripcion = "x", precio = 1, cantidad = 1)
            repo.insert(p)

            val list = repo.listadoProductos
            val value = list.value
            assertEquals(1, value?.size)
            assertEquals("RepoTest", value?.get(0)?.nombre)
        }
    }
}
