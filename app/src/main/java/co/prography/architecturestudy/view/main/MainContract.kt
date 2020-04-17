package co.prography.architecturestudy.view.main

import co.prography.architecturestudy.base.BaseContract

interface MainContract {
    interface View : BaseContract.BaseView<Presenter> {

    }

    interface Presenter : BaseContract.BasePresenter {
        fun fetchData()
        fun initRecyclerViewData(adapter: MainActivity.CityAdapter)
        fun updateFavorite(city: String, favorite: Int)
    }
}