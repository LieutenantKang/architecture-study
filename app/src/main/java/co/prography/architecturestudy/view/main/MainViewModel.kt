package co.prography.architecturestudy.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.prography.architecturestudy.data.model.MainModel
import co.prography.architecturestudy.data.room.City

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val model = MainModel(application)
    private val cities = model.getCities()

    fun getCities(): LiveData<List<City>> {
        return this.cities
    }

    fun fetchData() {
        model.fetchData()
    }

    fun updateFavorite(city: String, favorite: Int) {
        model.updateFavorite(city, favorite)
    }
}