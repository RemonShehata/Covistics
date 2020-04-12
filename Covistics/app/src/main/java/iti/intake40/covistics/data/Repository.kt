package iti.intake40.covistics.data

import androidx.lifecycle.LiveData
import iti.intake40.covistics.data.model.SingleCountryStats

interface Repository {
    fun getSavedCountryStats(): LiveData<List<SingleCountryStats>>
}