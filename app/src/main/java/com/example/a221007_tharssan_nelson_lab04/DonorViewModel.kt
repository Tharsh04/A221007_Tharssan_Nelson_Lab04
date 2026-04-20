package com.example.a221007_tharssan_nelson_lab04

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class DonorViewModel : ViewModel() {
    var userType by mutableStateOf<String?>(null)
    var donorName by mutableStateOf("")
    var donorEmail by mutableStateOf("")
    var donorMatric by mutableStateOf("")

    fun saveDonorInfo(name: String, email: String, matric: String) {
        donorName = name
        donorEmail = email
        donorMatric = matric
    }

    fun clearData() {
        donorName = ""
        donorEmail = ""
        donorMatric = ""
    }
}