package co.prography.architecturestudy.data.retrofit

import co.prography.architecturestudy.data.response.CityResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("/api/cities")
    fun getCities() : Call<CityResponse>
}