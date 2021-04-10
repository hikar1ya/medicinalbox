package com.example.medicinalbox.customlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.medicinalbox.R
import com.example.medicinalbox.database.MedicinalDatabase
import com.example.medicinalbox.databinding.CustomListFragmentBinding

class CustomListFragment : Fragment() {

    private lateinit var viewModel: CustomListViewModel

    private lateinit var binding: CustomListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.custom_list_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = CustomListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CustomListViewModel::class.java)

        val adapter = CustomListAdapter()
        binding.customLists.adapter = adapter
        adapter.viewModel = viewModel


        viewModel.customLists.observe(viewLifecycleOwner, Observer { groupList ->
            if (groupList != null) {
                adapter.data = groupList
            }
        })

        viewModel.navigateToCustomListInfo.observe(viewLifecycleOwner, Observer { item ->
            if (item != null) {
                this.findNavController().navigate(
                    CustomListFragmentDirections.actionCustomListFragmentToCustomListInfoFragment(
                        item.id)
                )
                viewModel.doneNavigating()
            }
        })

        binding.addCustomListButton.setOnClickListener {
            this.findNavController().navigate(
                CustomListFragmentDirections.actionCustomListFragmentToAddCustomListFragment()
            )
            Toast.makeText(requireActivity(), "Данные неполны", Toast.LENGTH_SHORT).show()
        }
        return binding.root

    }

}