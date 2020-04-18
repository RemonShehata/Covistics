package iti.intake40.covistics.core

import android.content.Context
import android.content.SharedPreferences

object CovidSharedPreferences {
    private val NAME = "Covid_Shared_Pref"
    private val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_COUNTRY_SUBSCRIBED_PREF = Pair("Is_Country_Subsrcibed", false)
    private val COUNTRY_NAME_PREF = Pair("Country_Name", "")
    private val CASES_PREF = Pair("Cases", "")
    private val DEATHS_PREF = Pair("Deaths", "")
    private val RECOVERED_PREF = Pair("Recovered", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isCountrySubscribed: Boolean
        get() = preferences.getBoolean(
            IS_COUNTRY_SUBSCRIBED_PREF.first,
            IS_COUNTRY_SUBSCRIBED_PREF.second
        )
        set(value) = preferences.edit() {
            it.putBoolean(IS_COUNTRY_SUBSCRIBED_PREF.first, value)
        }

    var countryName: String?
        get() = preferences.getString(COUNTRY_NAME_PREF.first, COUNTRY_NAME_PREF.second)
        set(value) = preferences.edit() {
            it.putString(COUNTRY_NAME_PREF.first, value)
        }

    var cases: String?
        get() = preferences.getString(CASES_PREF.first, CASES_PREF.second)
        set(value) = preferences.edit() {
            it.putString(CASES_PREF.first, value)
        }

    var deaths: String?
        get() = preferences.getString(DEATHS_PREF.first, DEATHS_PREF.second)
        set(value) = preferences.edit() {
            it.putString(DEATHS_PREF.first, value)
        }

    var recovered: String?
        get() = preferences.getString(RECOVERED_PREF.first, RECOVERED_PREF.second)
        set(value) = preferences.edit() {
            it.putString(RECOVERED_PREF.first, value)
        }
}