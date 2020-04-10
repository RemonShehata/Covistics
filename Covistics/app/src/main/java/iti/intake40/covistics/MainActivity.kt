package iti.intake40.covistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import iti.intake40.covistics.data.model.CountryStats
import iti.intake40.covistics.data.network.ApiClient
import iti.intake40.covistics.data.network.ApiInterface
import iti.intake40.covistics.view.adapters.CountryStatsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"//::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        recycler_view.adapter = CountryStatsAdapter(CountryStats.getCountryStatsList())
        var countryStatsList: MutableList<CountryStats> = ArrayList<CountryStats>()

//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("https://coronavirus-monitor.p.rapidapi.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val myApiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

        val call: Call<List<CountryStats>> = ApiClient.getClient.getAllCountryStats()

        call.enqueue(object : Callback<List<CountryStats>> {
            override fun onResponse(
                call: Call<List<CountryStats>>,
                response: Response<List<CountryStats>>
            ) {
                //response.body()?.let { countryStatsList.addAll(it) }
                countryStatsList.addAll(response!!.body()!!)
                Log.d(TAG, countryStatsList.toString())
            }

            override fun onFailure(call: Call<List<CountryStats>>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })

        Log.d(TAG, countryStatsList.toString())
    }
}
