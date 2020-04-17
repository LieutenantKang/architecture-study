package co.prography.architecturestudy.view

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.prography.architecturestudy.R
import co.prography.architecturestudy.data.response.CityResponse
import co.prography.architecturestudy.data.retrofit.RetrofitGenerator
import co.prography.architecturestudy.data.room.City
import co.prography.architecturestudy.data.room.CityDao
import co.prography.architecturestudy.data.room.CityDatabase
import kotlinx.android.synthetic.main.card_city.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var cityDatabase: CityDatabase
    lateinit var cityDao: CityDao
    private val recyclerView by lazy{
        findViewById<RecyclerView>(R.id.main_recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityDatabase = CityDatabase.getInstance(this)
        cityDao = cityDatabase.cityDao

        fetchData()

        val recyclerViewAdapter = CityAdapter()
        recyclerView.adapter = recyclerViewAdapter

        RecyclerViewUpdater(recyclerViewAdapter, cityDao).execute()
    }

    class RecyclerViewUpdater(private val adapter: CityAdapter, private val cityDao: CityDao) : AsyncTask<Void, Void, String>(){
        override fun doInBackground(vararg p0: Void?): String {
            adapter.setItem(cityDao.getCities() as ArrayList<City>)
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            adapter.notifyDataSetChanged()
        }
    }


    private fun fetchData(){
        val call = RetrofitGenerator.create().getCities()
        call.enqueue((object : Callback<CityResponse> {
            override fun onFailure(call: Call<CityResponse>, t: Throwable) {}

            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                Thread{cityDao.insertCities(response.body()?.cities)}.start()
            }
        }))
    }

    class CityAdapter: RecyclerView.Adapter<CityAdapter.ViewHolder>(){
        private lateinit var cityList : ArrayList<City>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_city,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val city = cityList[position]
            with(holder){
                cityName.text = cityList[position].city
            }
            holder.favoriteButton.setOnClickListener{
                // favorite edit
            }
        }

        override fun getItemCount(): Int = cityList.size

        fun setItem(cityList: ArrayList<City>){
            this.cityList = cityList
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val cityName : TextView = view.card_city_name
            val favoriteButton : ImageButton = view.card_city_button
        }
    }
}
