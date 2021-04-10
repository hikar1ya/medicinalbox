package com.example.medicinalbox.customlistinfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinalbox.database.MedicinalDatabaseDao
import com.example.medicinalbox.medicinallist.MedicinalListViewModel

@Suppress("UNCHECKED_CAST")
class CustomListInfoViewModelFactory(
    private val customListId: Long,
    val dao: MedicinalDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomListInfoViewModel::class.java)) {
            return CustomListInfoViewModel(customListId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}