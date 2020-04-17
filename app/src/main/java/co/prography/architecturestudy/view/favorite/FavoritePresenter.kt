package co.prography.architecturestudy.view.favorite

import android.content.Context
import co.prography.architecturestudy.data.model.FavoriteModel

class FavoritePresenter(private val view: FavoriteContract.View, context: Context) :
    FavoriteContract.Presenter {
    private var model: FavoriteModel = FavoriteModel(context)

    override fun start() {
        view.presenter = this
    }

    override fun initRecyclerViewData(adapter: FavoriteActivity.CityAdapter) {
        model.initRecyclerViewData(adapter)
    }
}