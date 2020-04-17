package co.prography.architecturestudy.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.prography.architecturestudy.R
import co.prography.architecturestudy.data.room.City
import kotlinx.android.synthetic.main.card_favorite.view.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.favorite_recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val adapter = CityAdapter()

        recyclerView.adapter = adapter

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.getCities().observe(this, Observer<List<City>> { cities ->
            adapter.setItem(cities)
        })
    }


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
            notifyDataSetChanged()
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityName: TextView = view.card_favorite_name
        }
    }
}
