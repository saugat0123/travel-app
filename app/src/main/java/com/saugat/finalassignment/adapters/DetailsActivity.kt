package com.saugat.finalassignment.adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.saugat.finalassignment.R
import com.saugat.rblibrary.api.ServiceBuilder
import com.saugat.rblibrary.entity.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var itemName: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemImg: ImageView
    private lateinit var addToCart: ImageView
    private lateinit var like: ImageView
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private var lat= 0.0
    private var lon= 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        itemName = findViewById(R.id.itemName)
        itemPrice = findViewById(R.id.itemPrice)
        itemImg = findViewById(R.id.itemImg)
        addToCart = findViewById(R.id.addToCart)

        val item: Item? = intent.getParcelableExtra("ITEM_DETAILS") as? Item
        println(item)
        if (item != null) {
            lat = item.latitude!!
            lon = item.longitude!!
            itemName.text = item.itemName
            itemPrice.text = (item.itemPrice).toString()
            val imagePath = ServiceBuilder.loadImagePath() + (item?.photo ?: item?.photo)
            Glide.with(this)
                .load(imagePath)
                .fitCenter()
                .into(itemImg)
        }
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Location to be shown on the map (latitude=22.22, longitude=22.333)
        val location = LatLng(lat, lon)

        // Add a marker at the specified location and move the camera
        mMap.addMarker(MarkerOptions().position(location).title("Marker at Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}