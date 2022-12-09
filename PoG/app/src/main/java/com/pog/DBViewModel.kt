package com.pog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DBViewModel(private val itemDao: ItemDao) : ViewModel() {
    private fun insertItem(item: DBItem) {

        viewModelScope.launch {
            itemDao.insert(item)
        }
    }
}


class DBViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DBViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}