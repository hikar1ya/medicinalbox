package com.example.medicinalbox.medicinallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
                adapter.data = elementsList
            }
        })

        binding.addMedicinalButton.setOnClickListener {
            this.findNavController().navigate(
                MedicinalListFragmentDirections.actionMedicinalListFragmentToAddMedicinalFragment()
            )
        }

//        viewModel.navigateToEdit.observe(viewLifecycleOwner, Observer { item ->
//            if (item != null) {
//                this.findNavController().navigate(
//                    MedicinalListFragmentDirections.actionMedicinalListFragmentToEditMedicinalFragment(
//                        item.id,
//                        item.name,
//                        item.dosage,
//                        item.formOfIssue,
//                        item.comment,
//                        item.amount
//                    )
//                )
//                viewModel.doneNavigating()
//            }
//        })
//

//
//        binding.button.setOnClickListener {
//            this.findNavController().navigate(
//                MedicinalListFragmentDirections.actionMedicinalListFragmentToGroupFragment()
//            )
//        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.filter(newText)
                }
                return true
            }
        })


        return binding.root
    }
}

