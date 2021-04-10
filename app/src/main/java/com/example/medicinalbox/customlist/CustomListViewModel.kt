package com.example.medicinalbox.customlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medicinalbox.database.CustomList
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class CustomListViewModel(val dao: MedicinalDatabaseDao,
                          application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var customLists = dao.getCustomLists()

    private val _navigateToList = MutableLiveData<CustomList>()
    val navigateToList: LiveData<CustomList>
        get() = _navigateToList

    fun editGroup(name: String) {
        uiScope.launch {
            val customList = CustomList()
            customList.name = name
            updateGroup(customList)
        }
    }

    private suspend fun updateGroup(customList: CustomList) {
        withContext(Dispatchers.IO) {
            dao.updateCustomList(customList)
        }
    }

    fun deleteCustomList(customList : CustomList){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteCustomList(customList)
                dao.deleteConnectionsByCustomListId(customList.id)
            }
        }
    }

    fun onGroupClicked(customList: CustomList) {
        uiScope.launch {
            _navigateToList.value = customList
        }
    }

    fun doneNavigating() {
        _navigateToList.value = null
    }

    private suspend fun insertElement(element: Medicinal) {
        withContext(Dispatchers.IO) {
            dao.insertMedicinal(element)
        }
    }

}