package iti.intake40.covistics.data

import androidx.lifecycle.LifecycleOwner
import iti.intake40.covistics.data.model.SingleCountryStats

interface Repository {
    fun getDataFromAPI()
    fun getDataFromDatabase(lifecycleOwner: LifecycleOwner)
    fun getCountriesData(lifecycleOwner: LifecycleOwner)
}