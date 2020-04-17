package co.prography.architecturestudy.view

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.prography.architecturestudy.R
import co.prography.architecturestudy.data.room.City
import co.prography.architecturestudy.data.room.CityDao
import co.prography.architecturestudy.data.room.CityDatabase
import kotlinx.android.synthetic.main.card_favorite.view.*

class FavoriteActivity : AppCompatActivity() {
    companion object {
        lateinit var favoriteCityDao: CityDao
    }

    private lateinit var cityDatabase: CityDatabase

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.favorite_recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        cityDatabase = CityDatabase.getInstance(this)
        favoriteCityDao = cityDatabase.cityDao

        val recyclerViewAdapter = CityAdapter()
        recyclerView.adapter = recyclerViewAdapter

        RecyclerViewUpdater(recyclerViewAdapter, favoriteCityDao).execute()
    }

    class RecyclerViewUpdater(private val adapter: CityAdapter, private val cityDao: CityDao) :
        AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            adapter.setItem(cityDao.getFavoriteCities())
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            adapter.notifyDataSetChanged()
        }
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
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityName: TextView = view.card_favorite_name
        }
    }
}
