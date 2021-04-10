package com.example.medicinalbox.addmedicinal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.medicinalbox.R
import com.example.medicinalbox.database.MedicinalDatabase
import com.example.medicinalbox.databinding.AddMedicinalFragmentBinding

class AddMedicinalFragment : Fragment() {

    private lateinit var viewModel: AddMedicinalViewModel

    private lateinit var binding: AddMedicinalFragmentBinding

    private fun checkData(): Boolean {
        return binding.name.text.toString() != "" && binding.dosage.text.toString() != "" &&
                binding.formOfIssue.text != null && binding.amount.text != null && binding.comment.text != null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_medicinal_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = AddMedicinalViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddMedicinalViewModel::class.java)


        binding.sendButton.setOnClickListener {
            if (checkData()) {
                viewModel.addNewMedicine(
                    binding.name.text.toString(),
                    binding.dosage.text.toString(),
                    binding.formOfIssue.text.toString(),
                    binding.amount.text.toString().toInt(),
                    binding.comment.text.toString()
                )
                this.findNavController().navigateUp()
            } else {
                Toast.makeText(requireActivity(), "Данные неполны", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}