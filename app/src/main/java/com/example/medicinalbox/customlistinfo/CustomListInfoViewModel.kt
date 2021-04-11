package com.example.medicinalbox.customlistinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class CustomListInfoViewModel(
    private val customListId: Long,
    val dao: MedicinalDatabaseDao
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val customList = dao.getCustomListById(customListId)
    val medicinalList = dao.getMedicinalsByCustomListId(customListId)

    private val _navigateToEdit = MutableLiveData<Boolean>()
    val navigateToEdit: LiveData<Boolean>
        get() = _navigateToEdit

    private val _navigateUp = MutableLiveData<Boolean>()
    val navigateUp: LiveData<Boolean>
        get() = _navigateUp

    fun onEdit() {
        uiScope.launch {
            _navigateToEdit.value = true
        }
    }

    fun onDelete() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                customList.value?.let { dao.deleteCustomList(it) }
                dao.deleteConnectionsByCustomListId(customListId)
            }
            _navigateUp.value = true
        }
    }

    fun doneNavigating() {
        _navigateToEdit.value = false
        _navigateUp.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}