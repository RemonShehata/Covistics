package iti.intake40.covistics.view.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import iti.intake40.covistics.R
import iti.intake40.covistics.data.model.SingleCountryStats
import iti.intake40.covistics.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.country_stats_item.view.*

class CountryStatsAdapter(
    val context: Context,
    val viewModel: MainViewModel,
    val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<CountryStatsAdapter.ViewHolder>() {

    var countriesList: List<SingleCountryStats> = ArrayList()
    var isSubscribed: Boolean = false
    var countryName: String? = null

    init {
        viewModel.getSharedPreferencesData(lifecycleOwner)
        viewModel.liveSharedPreferencesData.observe(lifecycleOwner, Observer {
            isSubscribed = it[0].toBoolean()
            countryName = it[1]
            notifyDataSetChanged()
        })
    }

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

        holder.itemView.flag_iv.setImageResource(R.drawable.ic_refresh)


        if (countriesList.get(position).countryName == countryName) {
            holder.itemView.subscribe_fab.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.death_color
                    )
                )
            )
        } else {
            holder.itemView.subscribe_fab.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        android.R.color.transparent
                    )
                )
            )
        }


        val pathString =
            "flags/".plus((countriesList.get(position).countryName).toLowerCase()).plus(".png")
        val ref = FirebaseStorage.getInstance().reference.child(pathString)
        Log.d("adapter", pathString)

        ref.downloadUrl.addOnSuccessListener {
            Glide.with(context)
                .load(it)
                .centerCrop()
                .into(holder.itemView.flag_iv)
        }


        holder.itemView.subscribe_fab.setOnClickListener(View.OnClickListener {
            Log.d(
                "TAG",
                countriesList.get(position).countryName
            )

            if (isSubscribed) {
                if (countriesList.get(position).countryName.equals(countryName)) {
                    holder.itemView.subscribe_fab.setBackgroundTintList(
                        ColorStateList.valueOf(
                            context.resources.getColor(
                                android.R.color.transparent
                            )
                        )
                    )
                    viewModel.setSharedPreferencesData(false, null)
                } else {
                    val alertBuilder = androidx.appcompat.app.AlertDialog.Builder(context)
                        .setTitle("Alert!!")
                        .setMessage(
                            "You are already subscribed to ${countryName}, Do you want to subscribe to ${countriesList.get(
                                position
                            ).countryName}"
                        )
                        .setNegativeButton("Yes") { dialogInterface, which ->
                            viewModel.setSharedPreferencesData(
                                true,
                                countriesList.get(position).countryName
                            )
                        }
                        .setPositiveButton("Cancel") { dialogInterface, which -> }
                    val alertDialog: androidx.appcompat.app.AlertDialog = alertBuilder.create()
                    alertDialog.show()
                }
            } else {
                holder.itemView.subscribe_fab.setBackgroundTintList(
                    ColorStateList.valueOf(
                        context.resources.getColor(
                            R.color.death_color
                        )
                    )
                )
                viewModel.setSharedPreferencesData(true, countriesList.get(position).countryName)
            }
        })
    }

    fun setCountryData(data: List<SingleCountryStats>) {
        this.countriesList = data
        notifyDataSetChanged()
    }
}