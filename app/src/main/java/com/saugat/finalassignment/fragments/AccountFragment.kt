package com.saugat.finalassignment.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.saugat.finalassignment.R
import com.saugat.finalassignment.ui.LoginActivity


@Suppress("CAST_NEVER_SUCCEEDS")
class AccountFragment : Fragment() {

    private lateinit var btnLogout: Button
    private lateinit var sharedPref: SharedPreferences
    private lateinit var recyclerViewProfile: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //@SuppressLint("UseRequireInsteadOfGet", "CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_account, container, false)

        btnLogout = view.findViewById(R.id.btnLogout)
        recyclerViewProfile = view.findViewById(R.id.recyclerViewProfile)
        sharedPref = requireContext().getSharedPreferences("MyPref",MODE_PRIVATE)

////        arguments?.getString("emailOfUser")
//        val args = arguments
//        val email = args?.getString("emailOfUser")

        loadProfile()

        btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(requireContext(),LoginActivity::class.java))
        }

        return view
    }

    private fun loadProfile() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val userRepo = UserRepo()
//                val response = userRepo.getMe()
//                if(response.success==true){
//                    // Put all the student details in lstStudents
//                    val lstUser = response.data
//                    withContext(Dispatchers.Main){
//                        val adapter = context?.let { CartAdapter(lstUser as ArrayList<User>, it) }
//                        recyclerViewProfile.layoutManager = LinearLayoutManager(activity)
//                        recyclerViewProfile.adapter = adapter
//                    }
//                }
//            }catch(ex : Exception){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(activity,
//                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }
}