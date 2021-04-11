package com.example.medicinalbox.addcustomlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinalbox.R
import com.example.medicinalbox.database.Medicinal

class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val elName: TextView = itemView.findViewById(R.id.name)
    val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
}

class AddCustomListAdapter() : RecyclerView.Adapter<ElementViewHolder>() {
    var data = listOf<Medicinal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var dataChecked = listOf<Medicinal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    lateinit var viewModel: AddCustomListViewModel

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val item = data[position]
        holder.elName.text = item.name
        if (dataChecked.contains(item)) {
            holder.checkBox.isChecked = true
        }
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                viewModel.addMedicinalToList(item)
            } else {
                viewModel.deleteMedicinalFromList(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_medicinal_list_add_custom_list, parent, false)
        return ElementViewHolder(view)
    }
}