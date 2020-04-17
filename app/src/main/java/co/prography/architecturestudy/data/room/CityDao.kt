package co.prography.architecturestudy.data.room

import androidx.room.*

@Dao
interface CityDao {
    @Query("SELECT * FROM City")
    fun getCities() : List<City>

    @Query("SELECT * FROM City WHERE favorite = 1")
    fun getFavoriteCities() : List<City>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCities(cities: List<City>?)

    @Query("UPDATE City SET favorite = :favorite WHERE city = :name")
    fun updateFavoriteCity(name : String, favorite: Int)
}