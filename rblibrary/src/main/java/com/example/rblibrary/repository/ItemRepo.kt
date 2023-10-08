package com.example.rblibrary.repository


import com.example.rblibrary.entity.Item
import com.example.rblibrary.response.AddItemResponse
import com.example.rblibrary.response.GetAlItemsResponse
import com.example.rblibrary.response.ImageResponse
import com.example.rblibrary.api.ItemAPI
import com.example.rblibrary.api.MyApiRequest
import com.example.rblibrary.api.ServiceBuilder
import okhttp3.MultipartBody


class ItemRepo: MyApiRequest() {
    private val itemAPI= ServiceBuilder.buildService(ItemAPI::class.java)

    suspend fun addItem(item: Item): AddItemResponse {
        return apiRequest {
            itemAPI.addItem(
                    ServiceBuilder.token!!, item
            )
        }
    }

    suspend fun getAllItems(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getAllItems(ServiceBuilder.token!!)
        }
    }

    suspend fun getClothes(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getClothes(ServiceBuilder.token!!)
        }
    }

    suspend fun getToiletries(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getToiletries(ServiceBuilder.token!!)
        }
    }

    suspend fun getGadgets(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getGadgets(ServiceBuilder.token!!)
        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            itemAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }
}