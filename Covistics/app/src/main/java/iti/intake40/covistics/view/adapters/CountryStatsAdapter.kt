package iti.intake40.covistics.view.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.SingleCountryStats
import kotlinx.android.synthetic.main.country_stats_item.view.*

class CountryStatsAdapter(val context: Context) :
    RecyclerView.Adapter<CountryStatsAdapter.ViewHolder>() {

    var countriesList: List<SingleCountryStats> = ArrayList()
    var isSubscribed: Boolean = false

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
        holder.itemView.new_cases_tv.text = countriesList.get(position).newCases
        holder.itemView.recovered_tv.text = countriesList.get(position).totalRecovered
        holder.itemView.deaths_tv.text = countriesList.get(position).deaths

        val flagUrl = "https://www.countryflags.io/".plus("us").plus("/shiny/64.png")
        Glide.with(holder.itemView)
            .load(flagUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_egypt_flag)
            .into(holder.itemView.flag_iv)

        holder.itemView.subscribe_fab.setOnClickListener(View.OnClickListener {
            Log.d(
                "TAG",
                countriesList.get(position).countryName
            )

            if (isSubscribed) {
                holder.itemView.subscribe_fab.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.death_color
                        )
                    )
                )
                isSubscribed = false
            } else {
                holder.itemView.subscribe_fab.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            android.R.color.transparent
                        )
                    )
                )
                isSubscribed = true
            }
        })
    }

    fun setCountryData(data: List<SingleCountryStats>) {
        this.countriesList = data
        notifyDataSetChanged()
    }
}