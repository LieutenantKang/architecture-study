package co.prography.architecturestudy.view.main

import android.content.Context
import co.prography.architecturestudy.data.model.MainModel

class MainPresenter(private val view: MainContract.View, context: Context) :
    MainContract.Presenter {
    private var model: MainModel = MainModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun fetchData() {
        model.fetchData()
    }

    override fun initRecyclerViewData(adapter: MainActivity.CityAdapter) {
        model.initRecyclerViewData(adapter)
    }

    override fun updateFavorite(city: String, favorite: Int) {
        model.updateFavorite(city, favorite)
    }
}