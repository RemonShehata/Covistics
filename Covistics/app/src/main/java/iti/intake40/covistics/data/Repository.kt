package iti.intake40.covistics.data

import androidx.lifecycle.LifecycleOwner

interface Repository {
    fun getDataFromAPI()
    fun getDataFromDatabase(lifecycleOwner: LifecycleOwner)
    fun getCountriesData(lifecycleOwner: LifecycleOwner)
    fun getWorldWideData()
    fun getSharedPreferencesData()
    fun setSharedPreferencesData(
        isCountrySubscribed: Boolean,
        countryName: String?,
        cases: String?,
        deaths: String?,
        recovered: String?
    )
}