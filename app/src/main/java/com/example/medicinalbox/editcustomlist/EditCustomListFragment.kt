package com.example.medicinalbox.editcustomlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.medicinalbox.R
import com.example.medicinalbox.addcustomlist.AddCustomListViewModel
import com.example.medicinalbox.database.MedicinalDatabase
import com.example.medicinalbox.databinding.EditCustomListFragmentBinding

class EditCustomListFragment : Fragment() {

    private lateinit var viewModel: EditCustomListViewModel

    private lateinit var binding: EditCustomListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_custom_list_fragment,
            container,
            false
        )

        val customListId = EditCustomListFragmentArgs.fromBundle(requireArguments()).id

        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = EditCustomListViewModelFactory(customListId, dao)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditCustomListViewModel::class.java)

        val adapter = EditCustomListAdapter()
        binding.medicinalList.adapter = adapter
        adapter.viewModel = viewModel

        viewModel.elements.observe(viewLifecycleOwner, Observer { elementsList ->
            if (elementsList != null) {
                adapter.data = elementsList
            }
        })

        binding.saveButton.setOnClickListener {
            viewModel.saveCustomList(binding.nameOfList.text.toString())
            this.findNavController().navigateUp()
        }

        return binding.root
    }

}