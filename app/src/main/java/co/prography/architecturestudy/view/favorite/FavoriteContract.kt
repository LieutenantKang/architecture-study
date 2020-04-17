package co.prography.architecturestudy.view.favorite

import co.prography.architecturestudy.base.BaseContract

interface FavoriteContract {
    interface View : BaseContract.BaseView<Presenter> {

    }

    interface Presenter : BaseContract.BasePresenter {
        fun initRecyclerViewData(adapter: FavoriteActivity.CityAdapter)
    }
}