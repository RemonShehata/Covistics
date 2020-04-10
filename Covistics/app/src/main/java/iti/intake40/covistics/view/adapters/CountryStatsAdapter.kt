package iti.intake40.covistics.view.adapters

import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.CountryStats
import kotlinx.android.synthetic.main.country_stats_item.view.*

class CountryStatsAdapter(var dataset: List<CountryStats>) :
    RecyclerView.Adapter<CountryStatsAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_stats_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.country_tv.text = dataset.get(position).countryName
        holder.itemView.confirmed_tv.text = dataset.get(position).activeCases.toString()
        holder.itemView.recovered_tv.text = dataset.get(position).totalRecovered.toString()
        holder.itemView.deaths_tv.text = dataset.get(position).deaths.toString()
    }
}