package co.prography.architecturestudy.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.prography.architecturestudy.R
import co.prography.architecturestudy.base.BaseActivity
import co.prography.architecturestudy.data.room.City
import co.prography.architecturestudy.view.favorite.FavoriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_city.view.*

class MainActivity : BaseActivity(), MainContract.View, View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_main

    override lateinit var presenter: MainContract.Presenter

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.main_recycler_view)
    }

    override fun initView() {
        presenter = MainPresenter(this@MainActivity, this)
        presenter.start()

        main_intent_button.setOnClickListener(this)

        presenter.fetchData()

        val recyclerViewAdapter = CityAdapter(presenter)
        recyclerView.adapter = recyclerViewAdapter

        presenter.initRecyclerViewData(recyclerViewAdapter)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_intent_button ->
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    override fun isViewActive(): Boolean = checkActive()

    class CityAdapter(private val presenter: MainContract.Presenter) :
        RecyclerView.Adapter<CityAdapter.ViewHolder>() {
        private var cityList = listOf<City>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_city, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val city = cityList[position]
            with(holder) {
                cityName.text = cityList[position].city
                if (city.favorite) {
                    favoriteButton.setBackgroundResource(R.drawable.ic_favorite_pink_24dp)
                } else {
                    favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border_pink_24dp)
                }
            }
            holder.favoriteButton.setOnClickListener {
                city.favorite = !city.favorite
                notifyDataSetChanged()
                Thread {
                    if (city.favorite) {
                        presenter.updateFavorite(city.city!!, 1)
                    } else {
                        presenter.updateFavorite(city.city!!, 0)
                    }
                }.start()
            }
        }

        override fun getItemCount(): Int = cityList.size

        fun setItem(cityList: List<City>) {
            this.cityList = cityList
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityName: TextView = view.card_city_name
            val favoriteButton: ImageButton = view.card_city_button
        }
    }
}
