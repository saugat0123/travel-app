package com.saugat.rblibrary.api

import com.saugat.rblibrary.entity.User
import com.saugat.rblibrary.response.GetUserProfileResponse
import com.saugat.rblibrary.response.ImageResponse
import com.saugat.finalassignment.response.LoginResponse
import com.saugat.rblibrary.response.UpdateUserResponse
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