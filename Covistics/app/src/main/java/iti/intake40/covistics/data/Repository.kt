package iti.intake40.covistics.data

import androidx.lifecycle.LiveData
import iti.intake40.covistics.data.model.SingleCountryStats

interface Repository {
    fun getDataFromAPI(): LiveData<List<SingleCountryStats>>
    fun getDataFromDatabase(): LiveData<List<SingleCountryStats>>
    fun getCountriesData(): LiveData<List<SingleCountryStats>>

}