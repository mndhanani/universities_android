package com.android.universities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.universities.data.University
import com.android.universities.repo.UniversitiesRepository
import com.android.universities.util.LOG_TAG
import com.android.universities.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: UniversitiesRepository,
) : ViewModel() {
    private val _universities = MutableLiveData<Result<List<University>>>()
    val universities: LiveData<Result<List<University>>> get() = _universities

    init {
        getUniversities()
    }

    private fun getUniversities() {
        viewModelScope.launch {
            repository.getUniversities().collectLatest { result ->
                Log.d(LOG_TAG, "getUniversities: result: $result")
                _universities.value = result
            }
        }
    }
}