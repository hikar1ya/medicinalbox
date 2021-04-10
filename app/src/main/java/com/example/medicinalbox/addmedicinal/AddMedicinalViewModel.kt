package com.example.medicinalbox.addmedicinal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class AddMedicinalViewModel(val dao: MedicinalDatabaseDao,
                            application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addNewMedicine(name: String, dosage: String, formOfIssue: String, amount: Int, comment: String) {
        uiScope.launch {
            val newMedicinal = Medicinal()
            newMedicinal.name = name
            newMedicinal.dosage = dosage
            newMedicinal.formOfIssue = formOfIssue
            newMedicinal.amount = amount
            newMedicinal.comment = comment
            insertDatabase(newMedicinal)
        }
    }

    private suspend fun insertDatabase(element: Medicinal) {
        withContext(Dispatchers.IO) {
            dao.insertMedicinal(element)
        }
    }
}