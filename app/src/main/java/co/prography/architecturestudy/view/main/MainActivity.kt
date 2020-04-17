package co.prography.architecturestudy.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.prography.architecturestudy.R
import co.prography.architecturestudy.data.room.City
import co.prography.architecturestudy.view.favorite.FavoriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_city.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.main_recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CityAdapter { city -> updateFavorite(city) }

        recyclerView.adapter = adapter

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.fetchData()

        mainViewModel.getCities().observe(this, Observer<List<City>> { cities ->
            adapter.setItem(cities)
        })

        main_intent_button.setOnClickListener(this)
    }

    private fun updateFavorite(city: City) {
        if (city.favorite) {
            mainViewModel.updateFavorite(city.city!!, 0)
        } else {
            mainViewModel.updateFavorite(city.city!!, 1)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_intent_button ->
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    class CityAdapter(val favoriteButtonClick: (City) -> Unit) :
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
                favoriteButtonClick(city)
            }
        }

        override fun getItemCount(): Int = cityList.size

        fun setItem(cityList: List<City>) {
            this.cityList = cityList
            notifyDataSetChanged()
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityName: TextView = view.card_city_name
            val favoriteButton: ImageButton = view.card_city_button
        }
    }
}
