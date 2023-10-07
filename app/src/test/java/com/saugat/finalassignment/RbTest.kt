package com.saugat.finalassignment

import com.saugat.rblibrary.entity.Item
import com.saugat.rblibrary.entity.User
import com.saugat.rblibrary.repository.ItemRepo
import com.saugat.rblibrary.repository.UserRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class RbTest {

    @Test
    fun checkLogin() = runBlocking{
        val userRepo = UserRepo()
        val response = userRepo.loginUser("admin@gmail.com", "admin123")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun registerUser()= runBlocking {
        val user =
                User(firstName = "fname", lastName = "lname", password = "password",
                        address = "address",phone = "phone",email = "mail2")
        val userRepo = UserRepo()
        val response = userRepo.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun addFood() = runBlocking {
        val food = Item(itemName= "name",itemType= "type", itemPrice= 200, itemRating= "rating")

        val itemRepository = ItemRepo()
        val response = itemRepository.addItem(food)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }
}