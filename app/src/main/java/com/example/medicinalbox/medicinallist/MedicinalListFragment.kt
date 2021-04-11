package com.example.medicinalbox.medicinallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medicinalbox.R
import com.example.medicinalbox.database.MedicinalDatabase
import com.example.medicinalbox.databinding.MedicinalListFragmentBinding

class MedicinalListFragment : Fragment() {

    private lateinit var viewModel: MedicinalListViewModel

    private lateinit var binding: MedicinalListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.medicinal_list_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = MedicinalListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MedicinalListViewModel::class.java)

        val adapter = MedicinalListAdapter()
        binding.elementsList.adapter = adapter
        adapter.viewModel = viewModel

        viewModel.elements.observe(viewLifecycleOwner, { elementsList ->
            if (elementsList != null) {
                viewModel.filteredElements = elementsList
                adapter.data = viewModel.filter(viewModel.search)
            }
        })

        binding.addMedicinalButton.setOnClickListener {
            this.findNavController().navigate(
                MedicinalListFragmentDirections.actionMedicinalListFragmentToAddMedicinalFragment()
            )
        }

        viewModel.navigateToEdit.observe(viewLifecycleOwner, { medicinal ->
            if (medicinal != null) {
                this.findNavController().navigate(
                    MedicinalListFragmentDirections.actionMedicinalListFragmentToEditMedicinalFragment(
                        medicinal.id
                    )
                )
                viewModel.doneNavigating()
            }
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.data = viewModel.filter(newText)
                }
                return true
            }
        })

        return binding.root
    }
}

