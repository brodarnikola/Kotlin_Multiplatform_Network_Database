package com.example.firstmultiplatformproject.shared

class Greeting {
    fun greeting(): String {
        return "Guest who is it:, ${Platform().platform.reversed()}!"
    }
}