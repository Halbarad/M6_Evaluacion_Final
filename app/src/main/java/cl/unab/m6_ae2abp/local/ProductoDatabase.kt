package cl.unab.m6_ae2abp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.unab.m6_ae2abp.modelo.ProductoRoom

@Database(entities = [ProductoRoom::class], version = 1, exportSchema = false)
abstract class ProductoDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: ProductoDatabase? = null

        fun getDatabase(context: Context): ProductoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}