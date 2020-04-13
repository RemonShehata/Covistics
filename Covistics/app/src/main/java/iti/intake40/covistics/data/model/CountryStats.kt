package iti.intake40.covistics.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "country_table")
data class SingleCountryStats(
    @PrimaryKey
    @ColumnInfo(name = "country_name")
    @Expose
    @SerializedName("country_name")
    var countryName: String,

    @ColumnInfo(name = "cases")
    @Expose
    var cases: String,

    @ColumnInfo(name = "deaths")
    @Expose
    var deaths: String,

    @ColumnInfo(name = "region")
    @Expose
    var region: String,

    @ColumnInfo(name = "total_recovered")
    @Expose
    @SerializedName("total_recovered")
    var totalRecovered: String,

    @ColumnInfo(name = "new_deaths")
    @Expose
    @SerializedName("new_deaths")
    var newDeaths: String,

    @ColumnInfo(name = "new_cases")
    @Expose
    @SerializedName("new_cases")
    var newCases: String,

    @ColumnInfo(name = "serious_critical")
    @Expose
    @SerializedName("serious_critical")
    var seriousCritical: String,

    @ColumnInfo(name = "active_cases")
    @Expose
    @SerializedName("active_cases")
    var activeCases: String,

    @ColumnInfo(name = "total_cases_per_1m_population")
    @Expose
    @SerializedName("total_cases_per_1m_population")
    var totalCasesPer1mPopulation: String
)
