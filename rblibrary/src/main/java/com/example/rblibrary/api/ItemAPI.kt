package com.example.rblibrary.api

import com.example.rblibrary.entity.Item
import com.example.rblibrary.response.AddItemResponse
import com.example.rblibrary.response.GetAlItemsResponse
import com.example.rblibrary.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ItemAPI {

    @POST ("/add/item")
    suspend fun addItem(
        @Header ("Authorization") token: String,
        @Body item: Item
    ):Response<AddItemResponse>

    @GET ("item/all")
    suspend fun getAllItems(
            @Header ("Authorization") token: String,
    ):Response<GetAlItemsResponse>

    @GET ("item/clothes")
    suspend fun getClothes(
            @Header ("Authorization") token: String,
    ):Response<GetAlItemsResponse>

    @GET ("item/toiletries")
    suspend fun getToiletries(
            @Header ("Authorization") token: String,
    ):Response<GetAlItemsResponse>

    @GET ("item/gadgets")
    suspend fun getGadgets(
            @Header ("Authorization") token: String,
    ):Response<GetAlItemsResponse>

    @Multipart
    @PUT("item/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}