package co.prography.architecturestudy.data.model

import android.content.Context
import co.prography.architecturestudy.data.room.CityDao
import co.prography.architecturestudy.data.room.CityDatabase
import co.prography.architecturestudy.view.favorite.FavoriteActivity

class FavoriteModel(context: Context) {
    private var database: CityDatabase = CityDatabase.getInstance(context)
    private var cityDao: CityDao

    init {
        cityDao = database.cityDao
    }

    fun initRecyclerViewData(adapter: FavoriteActivity.CityAdapter) {
        val thread = Thread { adapter.setItem(cityDao.getFavoriteCities()) }
        thread.start()

        try {
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        adapter.notifyDataSetChanged()
    }
}