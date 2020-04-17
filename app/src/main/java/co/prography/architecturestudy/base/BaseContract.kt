package co.prography.architecturestudy.base

interface BaseContract {
    interface BasePresenter {
        fun start()
    }

    interface BaseView<T> {
        var presenter: T
        fun isViewActive(): Boolean
    }
}