package com.example.medicinalbox.editmedicinal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.medicinalbox.database.Medicinal
import com.example.medicinalbox.database.MedicinalDatabaseDao
import kotlinx.coroutines.*

class EditMedicinalViewModel(val dao: MedicinalDatabaseDao,
                             application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun editMedicinal(id: Long, name: String, dosage: String, form_of_issue: String, amount: Int, comment: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val medicinal = dao.getMedicinalById(id)

                if (medicinal != null) {
                    medicinal.name = name
                    medicinal.dosage = dosage
                    medicinal.formOfIssue = form_of_issue
                    medicinal.amount = amount
                    medicinal.comment = comment
                    updateMedicinalDatabase(medicinal)
                }
            }
        }
    }

    private suspend fun updateMedicinalDatabase(element: Medicinal) {
        withContext(Dispatchers.IO) {
            dao.updateMedicinal(element)
        }
    }
}