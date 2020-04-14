package iti.intake40.covistics.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.SingleCountryStats
import kotlinx.android.synthetic.main.country_stats_item.view.*

class CountryStatsAdapter() :
    RecyclerView.Adapter<CountryStatsAdapter.ViewHolder>() {

    var countriesList: List<SingleCountryStats> = ArrayList()

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_stats_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = countriesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.country_tv.text = countriesList.get(position).countryName
        holder.itemView.confirmed_tv.text = countriesList.get(position).cases
        holder.itemView.recovered_tv.text = countriesList.get(position).totalRecovered
        holder.itemView.deaths_tv.text = countriesList.get(position).deaths
    }

    fun setCountryData(data: List<SingleCountryStats>) {
        this.countriesList = data
        notifyDataSetChanged()
    }
}