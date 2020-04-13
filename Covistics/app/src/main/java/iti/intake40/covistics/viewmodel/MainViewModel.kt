package iti.intake40.covistics.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.model.CountryStats

class MainViewModel : ViewModel() {
    init {

    }

    private val repository = RepositoryImpl()
    val liveCountryStats = MutableLiveData<List<CountryStats>>()

    fun getAllCountryStats() = repository.getSavedCountryStats()
}