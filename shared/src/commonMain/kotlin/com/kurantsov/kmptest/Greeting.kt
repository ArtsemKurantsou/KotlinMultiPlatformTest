package com.kurantsov.kmptest

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}