package com.android.universities.module_a.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.universities.common.data.University
import com.android.universities.module_a.databinding.ListItemUniversityBinding
import com.android.universities.module_a.util.UniversityDiffCallback

class UniversityAdapter(private var universities: List<University>) :
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

    /**
     * Refreshes the list of universities with new data.
     * It calculates the differences between the current and new list using DiffUtil,
     * and updates the adapter accordingly.
     */
    fun refreshList(newUniversities: List<University>) {
        val diffCallback = UniversityDiffCallback(universities, newUniversities)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        universities = newUniversities
        diffResult.dispatchUpdatesTo(this)
    }
}