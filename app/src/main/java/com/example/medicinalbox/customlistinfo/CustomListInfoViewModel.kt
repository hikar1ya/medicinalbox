package com.example.medicinalbox.customlistinfo

import androidx.lifecycle.ViewModel
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CustomListInfoViewModel(
    private val customListId: Long,
    val dao: MedicinalDatabaseDao
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val customList = dao.getCustomListById(customListId)
    val medicinalList = dao.getMedicinalsByCustomListId(customListId)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}