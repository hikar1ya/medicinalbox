package com.example.medicinalbox.editmedicinal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinalbox.database.MedicinalDatabaseDao

@Suppress("UNCHECKED_CAST")
class EditMedicinalViewModelFactory(
    private val dao: MedicinalDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMedicinalViewModel::class.java)) {
            return EditMedicinalViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}