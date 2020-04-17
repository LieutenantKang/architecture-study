package co.prography.architecturestudy.view.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.prography.architecturestudy.data.model.FavoriteModel
import co.prography.architecturestudy.data.room.City

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val model = FavoriteModel(application)
    private val cities = model.getFavoriteCities()

    fun getCities(): LiveData<List<City>> {
        return this.cities
    }
}