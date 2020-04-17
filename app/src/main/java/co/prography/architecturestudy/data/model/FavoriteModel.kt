package co.prography.architecturestudy.data.model

import android.app.Application
import androidx.lifecycle.LiveData
import co.prography.architecturestudy.data.room.City
import co.prography.architecturestudy.data.room.CityDao
import co.prography.architecturestudy.data.room.CityDatabase

class FavoriteModel(application: Application) {
    private var database: CityDatabase = CityDatabase.getInstance(application)
    private var cityDao: CityDao = database.cityDao

    fun getFavoriteCities(): LiveData<List<City>> {
        return cityDao.getFavoriteCities()
    }
}