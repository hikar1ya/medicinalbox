package com.example.medicinalbox.medicinallist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class MedicinalListViewModel(val dao: MedicinalDatabaseDao,
                             application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var elements = dao.getMedicinals()

    private val _navigateToEdit = MutableLiveData<Medicinal>()
    val navigateToEdit: LiveData<Medicinal>
        get() = _navigateToEdit

    fun onEditClicked(medicinal: Medicinal) {
        uiScope.launch {
            _navigateToEdit.value = medicinal
        }
    }

    fun doneNavigating() {
        _navigateToEdit.value = null
    }

    fun deleteMedicinal(medicinal : Medicinal){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteMedicinal(medicinal)
            }
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase()
        if (charText.length == 0) {
            elements = dao.getMedicinals()
        } else {
            elements = dao.filter(charText)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}