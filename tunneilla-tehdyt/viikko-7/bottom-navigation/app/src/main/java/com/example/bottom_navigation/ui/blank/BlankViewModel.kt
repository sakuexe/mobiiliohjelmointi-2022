package com.example.bottom_navigation.ui.blank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlankViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is blank fragment"
    }
    val text: LiveData<String> = _text
}