package com.example.medicinalbox.addcustomlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.medicinalbox.R
import com.example.medicinalbox.database.MedicinalDatabase
import com.example.medicinalbox.databinding.AddCustomListFragmentBinding

class AddCustomListFragment : Fragment() {

    private lateinit var viewModel: AddCustomListViewModel

    private lateinit var binding: AddCustomListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_custom_list_fragment,
            container,
            false
        )

        val adapter = ChooseMedicinalsAdapter()
        binding.groupList.adapter = adapter
        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = AddCustomListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddCustomListViewModel::class.java)
        adapter.viewModel = viewModel

        viewModel.elements.observe(viewLifecycleOwner, Observer { elementsList ->
            if (elementsList != null) {
                adapter.data = elementsList
            }
        })

        binding.saveButton.setOnClickListener {
            viewModel.saveCustomList(binding.nameOfGroup.text.toString())
            this.findNavController().navigateUp()
        }

        return binding.root
    }
}