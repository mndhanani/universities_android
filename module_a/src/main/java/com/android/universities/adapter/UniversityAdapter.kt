package com.android.universities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.universities.data.University
import com.android.universities.databinding.ListItemUniversityBinding
import com.android.universities.util.UniversityDiffCallback

class UniversityAdapter(
    private var universities: List<University>,
    private val listener: EventListener,
) :
    RecyclerView.Adapter<UniversityAdapter.ViewHolder>() {

    interface EventListener {
        fun onItemClicked(university: University)
    }

    class ViewHolder(val binding: ListItemUniversityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemUniversityBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            university = universities[position]
            listener = this@UniversityAdapter.listener
        }
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