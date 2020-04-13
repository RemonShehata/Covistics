package iti.intake40.covistics.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.intake40.covistics.data.model.SingleCountryStats

@Dao
interface CountryDAO {

    @Query("SELECT * FROM country_table order by cast(replace(cases, ',', '') as int) DESC")
    fun getAllCountries(): LiveData<List<SingleCountryStats>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<SingleCountryStats>)
}