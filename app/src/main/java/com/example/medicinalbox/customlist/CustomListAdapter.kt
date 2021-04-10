package com.example.medicinalbox.customlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinalbox.R
import com.example.medicinalbox.database.CustomList

class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val elName: TextView = itemView.findViewById(R.id.name)
    val editBtn: ImageView = itemView.findViewById(R.id.edit_button)
    val deleteBtn: ImageView = itemView.findViewById(R.id.delete_button)
}

class CustomListAdapter() : RecyclerView.Adapter<ElementViewHolder>() {
    var data = listOf<CustomList>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = data.size

    lateinit var viewModel: CustomListViewModel

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val item = data[position]
        holder.elName.text = "${item.name}"
        holder.editBtn.setOnClickListener {
            viewModel.onGroupClicked(item)
        }
        holder.deleteBtn.setOnClickListener {
            viewModel.deleteCustomList(item)
        }
        holder.elName.setOnClickListener {
            viewModel.deleteCustomList(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_custom_list, parent, false)
        return ElementViewHolder(view)
    }

}