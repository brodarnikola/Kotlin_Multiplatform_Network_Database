package com.example.firstmultiplatformproject

import com.example.firstmultiplatformproject.shared.Greeting
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check diordna is mentioned", Greeting().greeting().contains("Android".reversed()))
    }
}