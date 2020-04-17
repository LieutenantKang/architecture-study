package co.prography.architecturestudy.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM City")
    fun getCities(): LiveData<List<City>>

    @Query("SELECT * FROM City WHERE favorite = 1")
    fun getFavoriteCities(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCities(cities: List<City>?)

    @Query("UPDATE City SET favorite = :favorite WHERE city = :name")
    fun updateFavoriteCity(name: String, favorite: Int)
}