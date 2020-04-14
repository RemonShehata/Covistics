package iti.intake40.covistics.data.network

import iti.intake40.covistics.data.model.CountryStats
import iti.intake40.covistics.data.model.SubscribedCountryStat
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    val API_KEY: String
        get() = "45f01e3e9amsh28536b59fa24c6ep17d3c2jsnbf582a165bdf"

    @Headers(
        "x-rapidapi-host:coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 45f01e3e9amsh28536b59fa24c6ep17d3c2jsnbf582a165bdf"
    )
    @GET("coronavirus/cases_by_country.php")
    fun getAllCountryStats(): Call<CountryStats>

    @Headers(
        "x-rapidapi-host:coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 45f01e3e9amsh28536b59fa24c6ep17d3c2jsnbf582a165bdf"
    )
    @GET("coronavirus/latest_stat_by_country.php")
    fun getSubscribedCountryStat(@Query("country") country : String): Call<SubscribedCountryStat>
}