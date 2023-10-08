package com.example.suitcase.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitcase.R
import com.example.rblibrary.api.ServiceBuilder
import com.example.rblibrary.entity.Cart
import com.example.rblibrary.repository.CartRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(private val lstItems: ArrayList<Cart>, val context: Context)
    : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val phoneNumber = "9861357385"

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val foodName: TextView = view.findViewById(R.id.foodName)
        val foodPrice: TextView = view.findViewById(R.id.foodPrice)
        val foodImage: ImageView = view.findViewById(R.id.foodImage)
        val delete: ImageButton = view.findViewById(R.id.delete)
        val share: ImageButton = view.findViewById(R.id.share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_layout, parent, false)
        return CartViewHolder(view)

    }

    @SuppressLint("UnlocalizedSms")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = lstItems[position]
        holder.foodName.text = cart.itemName
        holder.foodPrice.text = cart.itemPrice
        val imagePath = ServiceBuilder.loadImagePath() + cart.photo
        if (!cart.photo.equals("no-photo.jpg")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.foodImage)
        }

        holder.share.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // If permission is granted, send SMS
                sendSMS("Hello World!")
            } else {
                // Request SMS permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(android.Manifest.permission.SEND_SMS),
                    SMS_PERMISSION_REQUEST_CODE
                )
            }
        }


        holder.delete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete item")
            builder.setMessage("Are you sure you want to delete ${cart.itemName} ?")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val cartRepo = CartRepo()
                        val response = cartRepo.deleteCartItem(cart._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        context,
                                        "${cart.itemName} deleted from Cart!!",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Dispatchers.Main) {
                                lstItems.remove(cart)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    ex.toString(),
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun sendSMS(message: String) {
        val smsManager = SmsManager.getDefault()

        try {
            // Send the SMS
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            // Show a success message to the user
            Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            // Handle exceptions, such as permissions or invalid phone number
            Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return lstItems.size
    }
    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 1
    }
}
