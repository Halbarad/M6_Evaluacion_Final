package cl.unab.m6_ae2abp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.unab.m6_ae2abp.dao.ProductoDao
import cl.unab.m6_ae2abp.modelo.Producto

@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
