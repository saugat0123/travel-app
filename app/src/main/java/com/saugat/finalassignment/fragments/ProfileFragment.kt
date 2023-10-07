package com.saugat.finalassignment.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.saugat.finalassignment.R
import com.saugat.finalassignment.ui.LoginActivity

import com.saugat.rblibrary.entity.User
import com.saugat.rblibrary.repository.UserRepo
import com.saugat.rblibrary.api.ServiceBuilder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var profilePic: CircleImageView
    private lateinit var fName: EditText
    private lateinit var lName: EditText
    private lateinit var address: EditText
    private lateinit var email: EditText
//    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnLogout: Button
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profilePic = view.findViewById(R.id.profilePic)
        fName = view.findViewById(R.id.fName)
        lName = view.findViewById(R.id.lName)
        address = view.findViewById(R.id.address)
        email = view.findViewById(R.id.email)
//        password = view.findViewById(R.id.password)
        phone = view.findViewById(R.id.phone)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnLogout = view.findViewById(R.id.btnLogout)

        loadAllUserDetails()

        profilePic.setOnClickListener {
            loadPopupMenu()
        }

        btnUpdate.setOnClickListener {
            updateDetails()
        }

        btnLogout.setOnClickListener {
            sharedPref = requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(requireActivity(),LoginActivity::class.java))
        }

        return view
    }

    private fun loadAllUserDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepo()
                val response = userRepo.getMe()
                if (response.success == true){
                    val imagePath = ServiceBuilder.loadImagePath() + response.data?.photo
                    withContext(Dispatchers.Main){
                        Glide.with(requireContext())
                                .load(imagePath)
                                .fitCenter()
                                .into(profilePic)

                        fName.setText(response.data?.firstName)
                        lName.setText(response.data?.lastName)
                        address.setText(response.data?.address)
                        email.setText(response.data?.email)
                        phone.setText(response.data?.phone)
//                        password.setText(response.data?.password)

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

    private fun updateDetails() {

        val fname = fName.text.toString()
        val lname = lName.text.toString()
        val addr = address.text.toString()
        val phn = phone.text.toString()
        val mail = email.text.toString()

        val user =
                User(firstName = fname, lastName = lname, address = addr, phone = phn, email = mail)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepo()
                val response = ServiceBuilder.id?.let { userRepo.updateUser(it,user) }
                if (response != null) {
                    if (response.success == true){
                        if (imageUrl != null){
                            uploadImage(response.data!!._id!!)
                        }
                        withContext(Dispatchers.Main){
                            Toast.makeText(activity,
                                    "Success", Toast.LENGTH_SHORT).show()


                        }
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

    private fun uploadImage(userId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                    MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepo()
                    val response = userRepo.userImageUpload(userId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("My Error ", ex.localizedMessage)
                        Toast.makeText(
                                requireActivity(),
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = requireActivity().contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                profilePic.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                profilePic.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun loadPopupMenu() {
        val popupMenu = PopupMenu(requireActivity(), profilePic)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

}