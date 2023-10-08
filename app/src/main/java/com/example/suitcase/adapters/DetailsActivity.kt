package com.example.suitcase.adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.suitcase.R
import com.example.rblibrary.api.ServiceBuilder
import com.example.rblibrary.entity.Cart
import com.example.rblibrary.entity.Item
import com.example.rblibrary.repository.CartRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var itemName: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemDes: TextView
    private lateinit var itemImg: ImageView
    private lateinit var addToCart: Button
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
        itemDes = findViewById(R.id.itemDes)

        val item: Item? = intent.getParcelableExtra("ITEM_DETAILS") as? Item
        println(item)
        if (item != null) {
            lat = item.latitude!!
            lon = item.longitude!!
            itemDes.text = item.itemDes
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

        addToCart.setOnClickListener {
            val name = item?.itemName
            val price = item?.itemPrice.toString()
            val pic = item?.photo

            val carts = Cart(itemName = name,photo = pic,itemPrice = price)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val cartRepo = CartRepo()
                    val response = cartRepo.addItemToCart(carts)
                    if(response.success == true){

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@DetailsActivity,
                                "$name Added to Cart", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DetailsActivity,
                            ex.toString(), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
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