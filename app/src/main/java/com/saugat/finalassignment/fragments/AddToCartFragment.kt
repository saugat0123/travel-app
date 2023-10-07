package com.saugat.finalassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saugat.finalassignment.R
import com.saugat.finalassignment.adapters.CartAdapter
import com.saugat.rblibrary.entity.Cart
import com.saugat.rblibrary.repository.CartRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddToCartFragment : Fragment() {

    private lateinit var recyclerViewCart: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart)

        loadCartItems()

        return view
    }

    private fun loadCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cartRepo = CartRepo()
                val response = cartRepo.getCartItems()
                if (response.success == true){
                    val lstItems = response.data
                    withContext(Dispatchers.Main){
                        val adapter = CartAdapter(lstItems as ArrayList<Cart>, requireActivity())
                        recyclerViewCart.layoutManager = LinearLayoutManager(requireActivity())
                        recyclerViewCart.adapter = adapter
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity,
                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}