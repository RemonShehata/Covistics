package iti.intake40.covistics.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryStats(
    @SerializedName("countries_stat")
    @Expose
    var countriesStat: List<SingleCountryStats>,
    @SerializedName("statistic_taken_at")
    @Expose
    var statsDate: String
)

data class SingleCountryStats(
    @Expose
    @SerializedName("country_name")
    var countryName: String,

    @Expose
    var cases: String,

    @Expose
    var deaths: String,

    @Expose
    var region: String,

    @Expose
    @SerializedName("total_recovered")
    var totalRecovered: String,

    @Expose
    @SerializedName("new_deaths")
    var newDeaths: String,

    @Expose
    @SerializedName("new_cases")
    var newCases: String,

    @Expose
    @SerializedName("serious_critical")
    var seriousCritical: String,

    @Expose
    @SerializedName("active_cases")
    var activeCases: String,

    @Expose
    @SerializedName("total_cases_per_1m_population")
    var totalCasesPer1mPopulation: String
)
