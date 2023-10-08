package com.example.rblibrary.api

import com.example.rblibrary.entity.User
import com.example.rblibrary.response.GetUserProfileResponse
import com.example.rblibrary.response.ImageResponse
import com.example.suitcase.response.LoginResponse
import com.example.rblibrary.response.UpdateUserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    // Register User
    @POST ("/register")
    suspend fun registerUser(
            @Body user: User)
    :Response<LoginResponse>

    // Login User
    @FormUrlEncoded
    @POST ("/login")
    suspend fun checkUser(
            @Field ("email") email: String,
            @Field ("password") password: String
    ): Response<LoginResponse>

    @GET("/me")
    suspend fun getMe(
            @Header ("Authorization") token: String,
    ): Response<GetUserProfileResponse>

    @PUT("/update/user/{id}")
    suspend fun updateUser(
            @Path("id") id: String,
            @Body user: User
    ): Response<UpdateUserResponse>

    @Multipart
    @PUT("/user/{id}/photo")
    suspend fun userImageUpload(
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}