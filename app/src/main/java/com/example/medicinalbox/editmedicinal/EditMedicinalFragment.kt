package com.example.medicinalbox.editmedicinal

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
import com.example.medicinalbox.databinding.EditMedicinalFragmentBinding

class EditMedicinalFragment : Fragment() {

    private lateinit var viewModel: EditMedicinalViewModel

    private lateinit var binding: EditMedicinalFragmentBinding

    private fun checkData(): Boolean {
        return binding.editName.text != null && binding.editDosage.text != null &&
                binding.editFormOfIssue.text != null && binding.editAmount.text != null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_medicinal_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = EditMedicinalViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditMedicinalViewModel::class.java)

        val args = EditMedicinalFragmentArgs.fromBundle(requireArguments())
        binding.editDosage.setText(args.dosage)
        binding.editName.setText(args.name)
        binding.editAmount.setText(args.amount.toString())
        binding.editFormOfIssue.setText(args.formOfIssue)
        binding.editComment.setText(args.comment)
        binding.sendButton.setOnClickListener {
            if (checkData()) {
                viewModel.editMedicinal(
                    args.id,
                    binding.editName.text.toString(),
                    binding.editDosage.text.toString(),
                    binding.editFormOfIssue.text.toString(),
                    binding.editAmount.text.toString().toInt(),
                    binding.editComment.text.toString()
                )
                this.findNavController().navigateUp()
            } else {
                Toast.makeText(requireActivity(), "Данные неполны", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}
