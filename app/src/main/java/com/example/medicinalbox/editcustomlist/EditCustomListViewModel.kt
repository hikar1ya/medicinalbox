package com.example.medicinalbox.editcustomlist

import androidx.lifecycle.ViewModel
import com.example.medicinalbox.database.CustomListMedicinalConnection
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class EditCustomListViewModel(
    private val customListId: Long,
    val dao: MedicinalDatabaseDao
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var customList = dao.getCustomListByIdLD(customListId)
    var elements = dao.getMedicinals()
    var medicinalList = dao.getMedicinalsByCustomListIdLD(customListId)

    var newMedicinalList : ArrayList<Medicinal> = arrayListOf()

    fun saveCustomList(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val customList = dao.getCustomListById(customListId)
                customList.name = name
                dao.updateCustomList(customList)
                val oldMedicinalList = dao.getMedicinalsByCustomListId(customListId) as ArrayList<Medicinal>
                for (medicinal in newMedicinalList) {
                    // добавляем новую связь, если добавлен новый медикамент
                    if (oldMedicinalList.contains(medicinal)) {
                        oldMedicinalList.remove(medicinal)
                    } else {
                        val connection = CustomListMedicinalConnection()
                        connection.customListId = customListId
                        connection.medicinalId = medicinal.id
                        dao.insertConnection(connection)
                    }
                }
                // если остались медикаменты, удаляем их
                if (oldMedicinalList.isNotEmpty()) {
                    oldMedicinalList.forEach{ medicinal ->
                        dao.deleteConnectionByCustomListAndMedicinal(customListId, medicinal.id)
                    }
                }
            }
        }
    }

    fun addMedicinalToList(item: Medicinal) {
        newMedicinalList.add(item)
    }

    fun deleteMedicinalFromList(item: Medicinal) {
        newMedicinalList.remove(item)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
