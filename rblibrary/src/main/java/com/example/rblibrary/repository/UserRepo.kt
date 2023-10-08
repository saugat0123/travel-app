package com.example.rblibrary.repository


import com.example.rblibrary.entity.User
import com.example.rblibrary.response.GetUserProfileResponse
import com.example.rblibrary.response.ImageResponse
import com.example.suitcase.response.LoginResponse
import com.example.rblibrary.response.UpdateUserResponse
import com.example.rblibrary.api.MyApiRequest
import com.example.rblibrary.api.ServiceBuilder
import com.example.rblibrary.api.UserAPI
import okhttp3.MultipartBody

class UserRepo : MyApiRequest() {
    private val userAPI= ServiceBuilder.buildService(UserAPI::class.java)

    //Register User
    suspend fun registerUser(user: User): LoginResponse{
        return apiRequest {
            userAPI.registerUser(user)
        }
    }

    //Login User
    suspend fun loginUser(email: String, password: String): LoginResponse{
        return apiRequest {
            userAPI.checkUser(email,password)
        }
    }

    suspend fun getMe(): GetUserProfileResponse {
        return apiRequest {
            userAPI.getMe(ServiceBuilder.token!!)
        }
    }

    suspend fun updateUser(id:String, user: User): UpdateUserResponse {
        return apiRequest {
            userAPI.updateUser(id, user)
        }
    }

    suspend fun userImageUpload(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            userAPI.userImageUpload(id, body)
        }
    }
}