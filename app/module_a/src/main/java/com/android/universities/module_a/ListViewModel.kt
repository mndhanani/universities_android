package com.android.universities.module_a

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.universities.common.data.University
import com.android.universities.common.repo.UniversitiesRepository
import com.android.universities.common.util.Result
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

    private fun getUniversities() {
        viewModelScope.launch {
            repository.getUniversities().collectLatest { result ->
                _universities.value = result
            }
        }
    }
}