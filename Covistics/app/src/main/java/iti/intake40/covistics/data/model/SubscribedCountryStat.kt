package iti.intake40.covistics.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubscribedCountryStat (
    @SerializedName("country")
    @Expose
    var country : String,
    @SerializedName("latest_stat_by_country")
    @Expose
    var countryLastestStat : List<SubscribedCountryData>
)

data class  SubscribedCountryData(
    @SerializedName("country_name")
    @Expose
    var countryName : String?,
    @SerializedName("total_cases")
    @Expose
    var cases : String?,
    @SerializedName("new_cases")
    @Expose
    var newCases : String?,
    @SerializedName("active_cases")
    @Expose
    var activeCases : String?,
    @SerializedName("total_deaths")
    @Expose
    var deaths : String?,
    @SerializedName("new_deaths")
    @Expose
    var newDeaths : String?,
    @SerializedName("total_recovered")
    @Expose
    var totalRecovered : String?,
    @SerializedName("serious_critical")
    @Expose
    var seriousCritical : String?,
    @SerializedName("region")
    @Expose
    var region : String?,
    @SerializedName("total_cases_per1m")
    @Expose
    var totalCasesPer1mPopulation : String
)