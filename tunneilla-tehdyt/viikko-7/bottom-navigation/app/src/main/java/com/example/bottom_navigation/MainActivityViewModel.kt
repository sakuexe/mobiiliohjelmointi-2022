package com.example.bottom_navigation

import androidx.lifecycle.MutableLiveData

data class Item(val text:String)

class MainActivityViewModel {
    val selected = MutableLiveData<Item>()

    fun select( item: Item ) {
        selected.value = item
    }
}