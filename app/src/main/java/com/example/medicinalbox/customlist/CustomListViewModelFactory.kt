package com.example.medicinalbox.customlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinalbox.database.MedicinalDatabaseDao
import com.example.medicinalbox.editmedicinal.EditMedicinalViewModel

@Suppress("UNCHECKED_CAST")
class CustomListViewModelFactory(
    private val dao: MedicinalDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomListViewModel::class.java)) {
            return CustomListViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}