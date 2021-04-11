package com.example.medicinalbox.editcustomlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicinalbox.database.MedicinalDatabaseDao
import com.example.medicinalbox.editmedicinal.EditMedicinalViewModel

@Suppress("UNCHECKED_CAST")
class EditCustomListViewModelFactory(
    private val customListId: Long,
    val dao: MedicinalDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMedicinalViewModel::class.java)) {
            return EditMedicinalViewModel(customListId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}