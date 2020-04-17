package co.prography.architecturestudy.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class], version = 2, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {
    abstract val cityDao: CityDao

    companion object {
        private var instance: CityDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CityDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java, "city.db"
                )
                    .fallbackToDestructiveMigration().build()
            }
            return instance as CityDatabase
        }
    }
}