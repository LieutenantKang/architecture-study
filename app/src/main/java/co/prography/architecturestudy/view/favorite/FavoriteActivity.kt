package co.prography.architecturestudy.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.prography.architecturestudy.R
import co.prography.architecturestudy.base.BaseActivity
import co.prography.architecturestudy.data.room.City
import kotlinx.android.synthetic.main.card_favorite.view.*

class FavoriteActivity : BaseActivity(), FavoriteContract.View {
    override val layoutRes: Int
        get() = R.layout.activity_favorite

    override lateinit var presenter: FavoriteContract.Presenter

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.favorite_recycler_view)
    }

    override fun initView() {
        presenter = FavoritePresenter(this@FavoriteActivity, this)
        presenter.start()

        val recyclerViewAdapter = CityAdapter()
        recyclerView.adapter = recyclerViewAdapter

        presenter.initRecyclerViewData(recyclerViewAdapter)
    }

    override fun isViewActive(): Boolean = checkActive()

    class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
        private var cityList = listOf<City>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_favorite, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                cityName.text = cityList[position].city
            }
        }

        override fun getItemCount(): Int = cityList.size

        fun setItem(cityList: List<City>) {
            this.cityList = cityList
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityName: TextView = view.card_favorite_name
        }
    }
}
