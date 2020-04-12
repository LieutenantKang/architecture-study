package co.prography.architecturestudy.data.room

import androidx.room.*

@Dao
interface CityDao {
    @Query("SELECT * FROM City")
    fun getFavoriteCities() : List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCity(city: City)

    @Delete
    fun deleteFavoriteCity(city: City)
}