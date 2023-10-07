package com.saugat.finalassignment.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saugat.finalassignment.R

import com.saugat.rblibrary.entity.Item
import com.saugat.finalassignment.fragments.CartFragment
import com.saugat.rblibrary.api.ServiceBuilder
import de.hdodenhof.circleimageview.CircleImageView

class ItemMenuAdapter(private val lstItems: ArrayList<Item>,
                      val context: Context)
    :RecyclerView.Adapter<ItemMenuAdapter.ItemViewHolder>()
{

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvItemName: TextView = view.findViewById(R.id.tvItemName)
        val tvItemType: TextView = view.findViewById(R.id.tvItemType)
        val tvItemRating: TextView = view.findViewById(R.id.tvItemRating)
        val tvItemPrice: TextView = view.findViewById(R.id.tvItemPrice)
        val itemImage: CircleImageView = view.findViewById(R.id.itemImage)
        val btnAddtoCart: ImageButton = view.findViewById(R.id.btnAddtoCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.dashboard_layout,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = lstItems[position]
        holder.tvItemName.text = item.itemName
        holder.tvItemType.text = item.itemType
        holder.tvItemRating.text = item.itemRating.toString()
        holder.tvItemPrice.text = item.itemPrice.toString()


        val imagePath = ServiceBuilder.loadImagePath() + item.photo
        if (!item.photo.equals("no-photo.jpg")) {
            Glide.with(context)
                    .load(imagePath)
                    .fitCenter()
                    .into(holder.itemImage)
        }

        holder.btnAddtoCart.setOnClickListener {
            val intent = Intent(context, CartFragment::class.java)
            var bundle = Bundle()
            bundle.putParcelable("item", item)
            intent.putExtra("myBundle", bundle)
            Toast.makeText(context, "${item.itemName} added to Cart!!", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return lstItems.size
    }
}