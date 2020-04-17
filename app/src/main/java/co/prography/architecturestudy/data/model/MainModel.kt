package co.prography.architecturestudy.data.model

import android.content.Context
import co.prography.architecturestudy.data.response.CityResponse
import co.prography.architecturestudy.data.retrofit.RetrofitGenerator
import co.prography.architecturestudy.data.room.CityDao
import co.prography.architecturestudy.data.room.CityDatabase
import co.prography.architecturestudy.view.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(context: Context) {
    private var database: CityDatabase = CityDatabase.getInstance(context)
    private var cityDao: CityDao

    init {
        cityDao = database.cityDao
    }

    fun fetchData() {
        val call = RetrofitGenerator.create().getCities()
        call.enqueue((object : Callback<CityResponse> {
            override fun onFailure(call: Call<CityResponse>, t: Throwable) {}

            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                Thread { cityDao.insertCities(response.body()?.cities) }.start()
            }
        }))
    }

    fun initRecyclerViewData(adapter: MainActivity.CityAdapter) {
        val thread = Thread { adapter.setItem(cityDao.getCities()) }
        thread.start()

        try {
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        adapter.notifyDataSetChanged()
    }

    fun updateFavorite(city: String, favorite: Int) {
        Thread { cityDao.updateFavoriteCity(city, favorite) }.start()
    }
}