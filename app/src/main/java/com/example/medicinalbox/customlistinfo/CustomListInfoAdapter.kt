package com.example.medicinalbox.customlistinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinalbox.R
import com.example.medicinalbox.database.Medicinal

class MedicinalListViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val dosage: TextView = itemView.findViewById(R.id.dosage)
    val amount: TextView = itemView.findViewById(R.id.amount)
    val comment: TextView = itemView.findViewById(R.id.comment)
    val formOfIssue: TextView = itemView.findViewById(R.id.formOfIssue)

    companion object {
        fun from(parent: ViewGroup): MedicinalListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_medical_custom_list_info, parent, false)
            return MedicinalListViewHolder(view)
        }
    }
}

class CustomListInfoAdapter : RecyclerView.Adapter<MedicinalListViewHolder>() {

    var data = listOf<Medicinal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var viewModel: CustomListInfoViewModel

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MedicinalListViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = "${item.name}"
        holder.dosage.text = "${item.dosage}"
        holder.amount.text = "${item.amount}"
        holder.comment.text = "${item.comment}"
        holder.formOfIssue.text = "${item.formOfIssue}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinalListViewHolder {
        return MedicinalListViewHolder.from(parent)
    }
}