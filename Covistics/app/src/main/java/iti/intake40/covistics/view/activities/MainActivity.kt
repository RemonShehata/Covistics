package iti.intake40.covistics.view.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import iti.intake40.covistics.view.UpdateBottomSheetDialog
import iti.intake40.covistics.R
import iti.intake40.covistics.core.Base
import iti.intake40.covistics.core.CovidSharedPreferences
import iti.intake40.covistics.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    UpdateBottomSheetDialog.BottomSheetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpDrawer()
        if (savedInstanceState == null) {
            //open the first fragment imdedaitely
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    StatisticsFragment()
                )
                .commit()
            //select the first item
            nav_view.setCheckedItem(R.id.nav_statistics)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_overview -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        OverviewFragment()
                    ).commit()
                toolbar.setTitle(R.string.overview)
            }

            R.id.nav_symptoms -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        SymptomsFragment()
                    ).commit()
                toolbar.setTitle(R.string.symptoms)
            }

            R.id.nav_prevention -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        PreventionFragment()
                    ).commit()
                toolbar.setTitle(R.string.prevention)
            }

            R.id.nav_treatment -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        TreatmentFragment()
                    ).commit()
                toolbar.setTitle(R.string.treatment)
            }

            R.id.nav_statistics -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        StatisticsFragment()
                    ).commit()
                toolbar.setTitle(R.string.countries)
            }

            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        SettingsFragment()
                    ).commit()
                toolbar.setTitle(R.string.settings)
            }

            R.id.nav_world -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        fragment_container.id,
                        WorldWideStatFragment()
                    ).commit()
                toolbar.setTitle(R.string.worldwide)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setUpDrawer() {
        toolbar.setTitle("Covistics")
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cab_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update_frequency_item) {
            val dialog = UpdateBottomSheetDialog()
            dialog.show(supportFragmentManager, "update_dialog")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onButtonClicked(interval: Long) {
        Base.cancelPeriodicWorker()
        Base.enqueuePeriodicWorker(interval)
        CovidSharedPreferences.periodicTime = interval
        Log.d("main", "interval = $interval")
    }
}

