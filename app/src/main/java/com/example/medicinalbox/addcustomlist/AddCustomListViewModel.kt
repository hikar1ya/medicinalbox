package com.example.medicinalbox.addcustomlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.medicinalbox.database.CustomList
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class AddCustomListViewModel(
    val dao: MedicinalDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val medicinalList: ArrayList<Medicinal> = arrayListOf()

    var elements = dao.getMedicinals()

    fun saveCustomList(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val customList = CustomList()
                customList.name = name
                dao.insertCustomList(customList)
                val id: Long = dao.getLastCustomList()
                for (medicinal in medicinalList) {
                    dao.addMedicinalToCustomList(medicinal.id, id)
                }
            }
        }
    }

    fun addMedicinalToList(item: Medicinal) {
        medicinalList.add(item)
    }

    fun deleteMedicinalFromList(item: Medicinal) {
        medicinalList.remove(item)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
