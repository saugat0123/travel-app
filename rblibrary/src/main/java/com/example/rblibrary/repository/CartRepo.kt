package com.example.rblibrary.repository

import com.example.rblibrary.api.CartAPI
import com.example.rblibrary.api.MyApiRequest
import com.example.rblibrary.api.ServiceBuilder
import com.example.rblibrary.entity.Cart
import com.example.rblibrary.response.*

class CartRepo: MyApiRequest() {

    private val cartAPI= ServiceBuilder.buildService(CartAPI::class.java)

    suspend fun addItemToCart(cart: Cart): AddCartResponse {
        return apiRequest {
            cartAPI.addItemToCart(
                    ServiceBuilder.token!!, cart
            )
        }
    }

    suspend fun getCartItems(): GetCartItemsResponse {
        return apiRequest {
            cartAPI.getCartItems(ServiceBuilder.token!!)
        }
    }

    suspend fun  deleteCartItem(id: String): DeleteCartResponse{
        return apiRequest {
            cartAPI.deleteCartItem(ServiceBuilder.token!!,id)
        }
    }
}