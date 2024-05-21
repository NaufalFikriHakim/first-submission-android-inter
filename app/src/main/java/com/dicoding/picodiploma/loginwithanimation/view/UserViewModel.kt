package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return userRepository.register(name, email, password)
    }
}