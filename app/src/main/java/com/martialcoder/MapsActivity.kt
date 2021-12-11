package com.martialcoder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.martialcoder.model.Data
import com.martialcoder.remotestate.R
import com.martialcoder.remotestate.databinding.ActivityMapsBinding
import com.martialcoder.viewmodel.MainViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewModel.getTRucksData(
            "PCH",
            33,
            false,
            "g2qb5jvucg7j8skpu5q7ria0mu",
            true,
            "lastRunningState,lastWaypoint"
        ).observe(this, {
            it?.let { stateData ->
                when (stateData
                    .status) {
                    StateData.DataStatus.SUCCESS -> {
                        val data = stateData.data?.data as ArrayList<Data>

                        for (i in 0 until data.size) {
                            val sydney = LatLng(data[i].lastWaypoint.lat, data[i].lastWaypoint.lng)
                            if (data[i].lastRunningState.truckRunningState == 1) {
                                mMap.addMarker(
                                    MarkerOptions().position(sydney)
                                        .icon(
                                            BitmapFromVector(
                                                applicationContext,
                                                R.drawable.car_green
                                            )
                                        )
                                )
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                            } else if (data[i].lastRunningState.truckRunningState == 0 && !data[i].lastWaypoint.ignitionOn) {
                                mMap.addMarker(
                                    MarkerOptions().position(sydney)
                                        .icon(
                                            BitmapFromVector(
                                                applicationContext,
                                                R.drawable.blue_car
                                            )
                                        )
                                )
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                            } else if (data[i].lastRunningState.truckRunningState == 0 && data[i].lastWaypoint.ignitionOn) {
                                mMap.addMarker(
                                    MarkerOptions().position(sydney)
                                        .icon(
                                            BitmapFromVector(
                                                applicationContext,
                                                R.drawable.yellow_car
                                            )
                                        )
                                )
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                            }
                        }
                    }
                    StateData.DataStatus.ERROR -> {
                        Toast.makeText(this, stateData.error?.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    StateData.DataStatus.LOADING -> {

                    }
                }
            }
        })
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

