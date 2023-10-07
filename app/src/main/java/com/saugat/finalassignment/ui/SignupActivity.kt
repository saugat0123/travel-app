package com.saugat.finalassignment.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.saugat.finalassignment.R
import com.saugat.rblibrary.entity.User
import com.saugat.rblibrary.repository.UserRepo
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

class SignupActivity : AppCompatActivity() {

    private lateinit var tvLogin: TextView
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPass: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var etMail: EditText
    private lateinit var btnSignup: Button
    private lateinit var rootLayout: LinearLayout
    private lateinit var imgProfile: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        tvLogin = findViewById(R.id.tvLogin)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPass = findViewById(R.id.etConfirmPass)
        etAddress = findViewById(R.id.etAddress)
        etPhone = findViewById(R.id.etPhone)
        etMail = findViewById(R.id.etMail)
        btnSignup = findViewById(R.id.btnSignup)
        rootLayout = findViewById(R.id.rootLayout)
        imgProfile = findViewById(R.id.imgProfile)

        imgProfile.setOnClickListener {
            loadPopupMenu()
        }

        btnSignup.setOnClickListener {
            registerUser()
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        val fname = etFirstName.text.toString()
        val lname = etLastName.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPass.text.toString()
        val address = etAddress.text.toString()
        val phone = etPhone.text.toString()
        val mail = etMail.text.toString()

        if (password != confirmPassword) {
            etConfirmPass.error = "Password does not match"
            etConfirmPass.requestFocus()
            return
        } else {
            val user =
                    User(firstName = fname, lastName = lname, password = password,
                            address = address, phone = phone, email = mail)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepo()
                    val response = userRepo.registerUser(user)
                    if (response.success == true) {
                        if (imageURL != null) {
                            uploadImage(response.data!!._id!!)
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@SignupActivity,
                                    "User Registered", Toast.LENGTH_SHORT
                            ).show()
                            emptyForm()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@SignupActivity,
                                "Error : ${ex.toString()}", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private var REQUEST_GALLERY_CODE = 3
    private var REQUEST_CAMERA_CODE = 2
    private var imageURL: String? = null

    private fun loadPopupMenu() {
        val popupMenu = PopupMenu(this, imgProfile)
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

    private fun uploadImage(userId: String) {
        if (imageURL != null) {
            val imageFile = File(imageURL!!)
            val requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            val body =
                    MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepo()
                    val response = userRepo.userImageUpload(userId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignupActivity, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("My Error ", ex.localizedMessage)
                        Toast.makeText(
                                this@SignupActivity,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageURL = cursor.getString(columnIndex)
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageURL))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageURL = file!!.absolutePath
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageURL))
            }
        }
    }

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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

    private fun emptyForm() {
        etFirstName.setText("")
        etLastName.setText("")
        etPassword.setText("")
        etConfirmPass.setText("")
        etAddress.setText("")
        etPhone.setText("")
        etMail.setText("")
        etFirstName.requestFocus()
    }
}