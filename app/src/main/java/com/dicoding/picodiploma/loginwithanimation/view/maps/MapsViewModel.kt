package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: UserRepository) : ViewModel() {
    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listReview: LiveData<List<ListStoryItem>> = _listStory

    init {
        getStoriesWithLocation()
    }

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            try {
                val response = repository.getStoriesWithLocation()
                _listStory.value = (response.listStory as List<ListStoryItem>?)!!
            } catch (_: Exception) {

            }
        }
    }
}