package com.example.medicinalbox.customlistinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.medicinalbox.R
import com.example.medicinalbox.database.MedicinalDatabase
import com.example.medicinalbox.databinding.CustomListInfoFragmentBinding

class CustomListInfoFragment : Fragment() {

    private lateinit var viewModel: CustomListInfoViewModel

    private lateinit var binding: CustomListInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.custom_list_info_fragment,
            container,
            false
        )

        val args = CustomListInfoFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(this.activity).application
        val dao = MedicinalDatabase.getInstance(application).getMedicinalDatabaseDao()
        val viewModelFactory = CustomListInfoViewModelFactory(args.id, dao)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CustomListInfoViewModel::class.java)

        val adapter = CustomListInfoAdapter()
        binding.groupList.adapter = adapter
        adapter.viewModel = viewModel

        viewModel.customList.observe(viewLifecycleOwner, Observer { customList ->
            if (customList != null) {
                binding.nameOfList.text = customList.name
            }
        })

        viewModel.medicinalList.observe(viewLifecycleOwner, Observer { medicinalList ->
            if (medicinalList != null) {
                adapter.data = medicinalList
            }
        })


        return binding.root
    }
}