package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun saveSession(user: UserModel){
        userRepository.saveSession(user)
    }
    fun getSession() = userRepository.getSession()

    suspend fun logout() = userRepository.logout()

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return userRepository.register(name, email, password)
    }
}