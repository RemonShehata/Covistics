package iti.intake40.covistics.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import iti.intake40.covistics.data.model.SingleCountryStats

@Database(entities = arrayOf(SingleCountryStats::class), version = 1, exportSchema = false)
public abstract class CountryRoomDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDAO

    companion object {

        @Volatile
        private var INSTANCE: CountryRoomDatabase? = null

        fun getDatabase(context: Context): CountryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountryRoomDatabase::class.java,
                    "country_stats_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}