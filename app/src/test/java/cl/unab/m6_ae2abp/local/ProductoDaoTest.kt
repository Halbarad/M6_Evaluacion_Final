package cl.unab.m6_ae2abp.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import cl.unab.m6_ae2abp.modelo.ProductoRoom
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import cl.unab.m6_ae2abp.util.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [33])
class ProductoDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ProductoDatabase
    private lateinit var dao: ProductoDao

    @Before
    fun createDb() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        db = Room.inMemoryDatabaseBuilder(context, ProductoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.productoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_and_findAll_returnsInserted() = runBlocking {
        val producto = ProductoRoom(nombre = "A", descripcion = "desc", precio = 100, cantidad = 1)
        dao.insert(producto)

        val list = dao.findAll().getOrAwaitValue()
        assertEquals(1, list.size)
        assertEquals("A", list[0].nombre)
    }

    @Test
    fun update_and_delete_behaviour() = runBlocking {
        val producto = ProductoRoom(nombre = "B", descripcion = "d", precio = 10, cantidad = 2)
        dao.insert(producto)

        // LiveData trae el id autogenerado; obtener el primer elemento
        var list = dao.findAll().getOrAwaitValue()
        val inserted = list[0]

        val updated = inserted.copy(nombre = "B-modified")
        dao.update(updated)

        list = dao.findAll().getOrAwaitValue()
        assertEquals("B-modified", list[0].nombre)

        dao.delete(updated)
        list = dao.findAll().getOrAwaitValue()
        assertEquals(0, list.size)
    }
}
