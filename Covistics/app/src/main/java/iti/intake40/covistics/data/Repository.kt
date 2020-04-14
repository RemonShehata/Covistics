package iti.intake40.covistics.data

import iti.intake40.covistics.data.model.SingleCountryStats

interface Repository {
    fun getDataFromAPI()
    fun getDataFromDatabase()
    fun getCountriesData()
}