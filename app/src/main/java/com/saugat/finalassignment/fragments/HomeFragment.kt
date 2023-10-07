package com.saugat.finalassignment.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saugat.finalassignment.R
import com.saugat.finalassignment.adapters.ItemsAdapter

import com.saugat.rblibrary.entity.Item
import com.saugat.rblibrary.repository.ItemRepo
import com.saugat.rblibrary.repository.UserRepo
import com.saugat.rblibrary.api.ServiceBuilder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {
    private lateinit var rvItems: RecyclerView
    private lateinit var drink: ImageView
    private lateinit var vege: ImageView
    private lateinit var nonVege: ImageView
    private lateinit var tvFname: TextView
    private lateinit var profileImg: CircleImageView

    var i =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.custom_layout, container, false)
        rvItems = view.findViewById(R.id.rvItems)
        drink = view.findViewById(R.id.drink)
        vege = view.findViewById(R.id.vege)
        nonVege = view.findViewById(R.id.nonVege)
        tvFname = view.findViewById(R.id.tvFname)
        profileImg = view.findViewById(R.id.profileImg)

        loadUserDetails()
        loadItems()

        drink.setOnClickListener {
            loadDrinks()
        }

        vege.setOnClickListener {
            loadVege()
        }

        nonVege.setOnClickListener{
            loadNonVege()
        }

        return view
    }

    private fun loadUserDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepo()
                val response = userRepo.getMe()
                if (response.success == true){
                    val fName = response.data?.firstName
//                    val profilePic = response.data?.photo
                    val imagePath = ServiceBuilder.loadImagePath() + response.data?.photo
                    withContext(Dispatchers.Main){
                        tvFname.text = fName
                        Glide.with(requireContext())
                                .load(imagePath)
                                .fitCenter()
                                .into(profileImg)

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

    @SuppressLint("WrongConstant")
    private fun loadNonVege() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemRepo = ItemRepo()
                val response = itemRepo.getNonVege()
                if (response.success == true){
                    val lstItems = response.data
                    withContext(Dispatchers.Main){
                        val adapter = ItemsAdapter(lstItems as ArrayList<Item>, requireActivity())
                        rvItems.layoutManager = LinearLayoutManager(context,OrientationHelper.HORIZONTAL, false)
                        rvItems.adapter = adapter
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

    @SuppressLint("WrongConstant")
    private fun loadVege() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemRepo = ItemRepo()
                val response = itemRepo.getVege()
                if (response.success == true){
                    val lstItems = response.data
                    withContext(Dispatchers.Main){
                        val adapter = ItemsAdapter(lstItems as ArrayList<Item>, requireActivity())
                        rvItems.layoutManager = LinearLayoutManager(context,OrientationHelper.HORIZONTAL, false)
                        rvItems.adapter = adapter
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

    @SuppressLint("WrongConstant")
    private fun loadDrinks() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemRepo = ItemRepo()
                val response = itemRepo.getDrinks()
                if (response.success == true){
                    val lstItems = response.data
                    withContext(Dispatchers.Main){
                        val adapter = ItemsAdapter(lstItems as ArrayList<Item>, requireActivity())
                        rvItems.layoutManager = LinearLayoutManager(context,OrientationHelper.HORIZONTAL, false)
                        rvItems.adapter = adapter
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

    @SuppressLint("WrongConstant")
    private fun loadItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemRepo = ItemRepo()
                val response = itemRepo.getAllItems()
                if (response.success == true){
                    val lstItems = response.data
                    withContext(Dispatchers.Main){
                        val adapter = ItemsAdapter(lstItems as ArrayList<Item>, requireActivity())
                        rvItems.layoutManager = LinearLayoutManager(context,OrientationHelper.HORIZONTAL, false)
                        rvItems.adapter = adapter
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