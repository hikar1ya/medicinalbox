package com.example.medicinalbox.addcustomlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.medicinalbox.database.CustomList
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class AddCustomListViewModel(val dao: MedicinalDatabaseDao,
                             application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val group: ArrayList<Medicinal> = arrayListOf()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var elements = dao.getMedicinals()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun saveCustomList(name: String){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val customList = CustomList()
                customList.name = name
                dao.insertCustomList(customList)
                val id : Long = dao.getLastCustomList()
                for(medicinal in group){
                    dao.addMedicinalToCustomList(medicinal.id, id)
                }
            }
        }
    }

    fun addItemToGroup(item : Medicinal){
        group.add(item)
    }

    fun deleteItemFromGroup(item : Medicinal){
        group.remove(item)
    }

    private fun initializeList() {
        uiScope.launch {
            elements = dao.getMedicinals()
        }
    }

    fun deleteMedicinal(medicinal : Medicinal){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteMedicinal(medicinal)
            }
        }
    }

}
