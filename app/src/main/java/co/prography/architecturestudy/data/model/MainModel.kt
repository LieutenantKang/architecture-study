package co.prography.architecturestudy.data.model

import android.app.Application
import androidx.lifecycle.LiveData
import co.prography.architecturestudy.data.response.CityResponse
import co.prography.architecturestudy.data.retrofit.RetrofitGenerator
import co.prography.architecturestudy.data.room.City
import co.prography.architecturestudy.data.room.CityDao
import co.prography.architecturestudy.data.room.CityDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(application: Application) {
    private var database: CityDatabase = CityDatabase.getInstance(application)
    private var cityDao: CityDao = database.cityDao

    fun fetchData() {
        val call = RetrofitGenerator.create().getCities()
        call.enqueue((object : Callback<CityResponse> {
            override fun onFailure(call: Call<CityResponse>, t: Throwable) {}

            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                Thread { cityDao.insertCities(response.body()?.cities) }.start()
            }
        }))
    }

    fun getCities(): LiveData<List<City>> {
        return cityDao.getCities()
    }

    fun updateFavorite(city: String, favorite: Int) {
        Thread { cityDao.updateFavoriteCity(city, favorite) }.start()
    }
}