package com.android.universities.module_a.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.universities.common.data.University
import com.android.universities.module_a.databinding.ListItemUniversityBinding

class UniversityAdapter(private val universities: List<University>) :
    RecyclerView.Adapter<UniversityAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemUniversityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemUniversityBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.university = universities[position]
    }

    override fun getItemCount() = universities.size
}