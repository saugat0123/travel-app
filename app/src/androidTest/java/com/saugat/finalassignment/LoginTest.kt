package com.saugat.finalassignment

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.saugat.finalassignment.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class LoginTest {

    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLoginUI(){
        onView(withId(R.id.etEmail))
                .perform(ViewActions.typeText("admin@gmail.com"))

        onView(withId(R.id.etPassword))
                .perform(ViewActions.typeText("admin123"))

        closeSoftKeyboard()

        onView(withId(R.id.btnLogin))
                .perform(ViewActions.click())

        onView(withId(R.id.tvResult))
                .check(matches(withText("Hi, User")))
    }
}