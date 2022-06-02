package com.campingstudio.agencekotlin.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.campingstudio.agencekotlin.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.Toast
import com.google.android.gms.maps.OnMapReadyCallback

class MapFragment : Fragment() , OnMapReadyCallback ,GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener{
    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        (childFragmentManager.findFragmentById(R.id.frg) as SupportMapFragment?)?.getMapAsync { mMap ->
            this.mMap = mMap
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
           // addAgenceBrMarker()
           // navigateToAgenceMarker()
            enableMyLocation()
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
        }
        return rootView
    }

    private fun navigateToAgenceMarker() {
        val latLongForCamera = CameraPosition.builder()
            .target(LatLng(-23.66255469273391, -46.70944611534271))
            .zoom(10f)
            .bearing(0f)
            .tilt(45f)
            .build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(latLongForCamera), 10000, null)

    }

    //Check whether the real-time location permission has been accepted or not.
    private fun isPermissionsGranted() = this.context?.let {
        ContextCompat.checkSelfPermission(it,Manifest.permission.ACCESS_FINE_LOCATION)
    } == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        this.context?.let {
            if (!::mMap.isInitialized) return
            if (isPermissionsGranted()) {
                if (ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestLocationPermission()
                    return
                }
                mMap.isMyLocationEnabled = true
            } else {
                requestLocationPermission()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocation()
    }

    private fun requestLocationPermission() {
            this.activity?.let {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this.context, "Ve a ajustes y acepta los permisos", Toast
                        .LENGTH_SHORT).show()
                } else {
                    ActivityCompat.requestPermissions(it,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_CODE_LOCATION )
                }
            }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        this.context.let {
            when(requestCode){
                REQUEST_CODE_LOCATION -> if
                        (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    mMap.isMyLocationEnabled = true
                }else{
                    Toast.makeText(it, "Para activar la localización ve a ajustes y acepta los " +
                            "permisos", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun addAgenceBrMarker() {
        mMap.clear() //clear old markers
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(-23.66255469273391, -46.70944611534271))
                .title("Agence Consultoria e Desenvolvimento Web e Mobile")
                .snippet("R. Benedito Fernandes, 545 - Santo Amaro, São Paulo - SP, 04746-110, Brasil")
        )
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this.context, "Estás en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this.context, "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }
}// Required empty public constructor