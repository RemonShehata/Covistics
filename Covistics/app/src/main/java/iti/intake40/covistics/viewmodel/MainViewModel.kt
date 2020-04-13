package iti.intake40.covistics.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import iti.intake40.covistics.data.RepositoryImpl
import iti.intake40.covistics.data.database.CountryDAO
import iti.intake40.covistics.data.database.CountryRoomDatabase
import iti.intake40.covistics.data.model.CountryStats
import iti.intake40.covistics.data.model.SingleCountryStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: CountryDAO
    private val repository: RepositoryImpl
    val liveCountryStats: MutableLiveData<List<CountryStats>>

    init {
        dao = CountryRoomDatabase.getDatabase(application).countryDao()
        repository = RepositoryImpl(dao, getApplication())
        liveCountryStats = MutableLiveData<List<CountryStats>>()
    }

    fun getAllCountryStats() = repository.getCountriesData()

    fun saveToDatabase(countries: List<SingleCountryStats>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(countries)
        }

}