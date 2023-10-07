package com.saugat.rblibrary.repository


import com.saugat.rblibrary.entity.Item
import com.saugat.rblibrary.response.AddItemResponse
import com.saugat.rblibrary.response.GetAlItemsResponse
import com.saugat.rblibrary.response.ImageResponse
import com.saugat.rblibrary.api.ItemAPI
import com.saugat.rblibrary.api.MyApiRequest
import com.saugat.rblibrary.api.ServiceBuilder
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

    suspend fun getDrinks(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getDrinks(ServiceBuilder.token!!)
        }
    }

    suspend fun getVege(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getVege(ServiceBuilder.token!!)
        }
    }

    suspend fun getNonVege(): GetAlItemsResponse {
        return apiRequest {
            itemAPI.getNonVege(ServiceBuilder.token!!)
        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            itemAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }
}