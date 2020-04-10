package iti.intake40.covistics.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryStats(
    @Expose
    @SerializedName("country_name")
    var countryName: String,
    @Expose
    var cases: Int,
    @Expose
    var deaths: Int,
    @Expose
    var region: String,
    @Expose
    @SerializedName("total_recovered")
    var totalRecovered: Int,
    @Expose
    @SerializedName("new_deaths")
    var newDeaths: Int,
    @Expose
    @SerializedName("new_cases")
    var newCases: Int,
    @Expose
    @SerializedName("serious_critical")
    var seriousCritical: Int,
    @Expose
    @SerializedName("active_cases")
    var activeCases: Int,
    @Expose
    @SerializedName("total_cases_per_1m_population")
    var totalCasesPer1mPopulation: Int
) {
    companion object {
        fun getCountryStatsList(): List<CountryStats> {
            TODO()
        }
    }
}