package com.rootminusone.spacestationpasses

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.rootminusone.spacestationpasses.adapter.PassesAdapter
import com.rootminusone.spacestationpasses.model.Response
import java.util.*

/**
 * Created by gangares on 12/10/17.
 */

class SpaceStationPassesActivity : AppCompatActivity(), StationPassesContractHub.View {
    private var recyclerView: RecyclerView? = null
    private var adapter: PassesAdapter? = null
    private var passesList: ArrayList<Response> = ArrayList()
    private var presenter: StationPassesContractHub.Presenter? = null
    private val GPS_FINE_LOCATION: Int = 1
    private var currentOrientation: Int? = null
    private var isOrientationChanged: Boolean = false
    private val passesBundleKey: String = "passesBundleKey"

    override fun displayPasses(passesList: ArrayList<Response>?) {
        if (passesList != null) {
            adapter = PassesAdapter(this, passesList)
            this.passesList = passesList
            recyclerView?.adapter = PassesAdapter(this, passesList)
        }
    }

    override fun displayError(errorMessage: String?) {
        //Handle displaying of any error scenarios here.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_station_passes)
        presenter = PassesPresenter(this)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        passesList = ArrayList()
        adapter = PassesAdapter(this, passesList)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        currentOrientation = this.resources.configuration.orientation
        if (savedInstanceState?.getParcelableArray(passesBundleKey) != null) {
            displayPasses(savedInstanceState.getParcelableArrayList(passesBundleKey))
        } else {
            fetchPassesByLocation()
        }
    }

    private fun fetchPassesByLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGPSEnable) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getLocationAndLoadPasses(true)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Handle showing more explanation of why we need GPS permission, currently we use Network to determine user location if GPS permission is denied.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            Array(1, { Manifest.permission.ACCESS_FINE_LOCATION }),
                            GPS_FINE_LOCATION);

                }
            }
        } else {
            getLocationAndLoadPasses(false)
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation != currentOrientation) {
            isOrientationChanged = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (isOrientationChanged) {
            outState?.putParcelableArrayList(passesBundleKey, passesList)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            GPS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    getLocationAndLoadPasses(true)
                } else {
                    // permission denied
                    getLocationAndLoadPasses(false)
                }
                return
            }
        }
    }

    private fun getLocationAndLoadPasses(isGPSEnabled: Boolean) {
        var location: Location? = null
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        var lat = ""
        var long = ""
        if (isGPSEnabled) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        if (location != null) {
            lat = location.latitude.toString()
            long = location.longitude.toString()
        }
        val locationMap: HashMap<String, String> = HashMap()
        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(long)) {
            // handle getting long and lat error conditions here
        }
        locationMap.put("lat", lat)
        locationMap.put("lon", long)
        presenter?.loadPasses(locationMap)
    }
}
