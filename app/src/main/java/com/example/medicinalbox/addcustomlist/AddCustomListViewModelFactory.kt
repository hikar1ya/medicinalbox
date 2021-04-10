package com.example.medicinalbox.addcustomlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinalbox.database.MedicinalDatabaseDao

@Suppress("UNCHECKED_CAST")
class AddCustomListViewModelFactory(
    private val dao: MedicinalDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCustomListViewModel::class.java)) {
            return AddCustomListViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}