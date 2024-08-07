package com.android.universities.module_a.util

import androidx.recyclerview.widget.DiffUtil
import com.android.universities.common.data.University

/**
 * A DiffUtil.Callback implementation used to calculate the differences between
 * two lists of University objects. This helps in efficiently updating a RecyclerView
 * by determining which items have changed.
 *
 * As there is no unique ID available for the universities, the comparison is
 * based on the name and web page of each university.
 *
 * @param oldList The old list of universities.
 * @param newList The new list of universities.
 */
class UniversityDiffCallback(
    private val oldList: List<University>,
    private val newList: List<University>,
) : DiffUtil.Callback() {

    /**
     * Returns the size of the old list.
     */
    override fun getOldListSize(): Int = oldList.size

    /**
     * Returns the size of the new list.
     */
    override fun getNewListSize(): Int = newList.size

    /**
     * Checks whether two items represent the same university by comparing their name and web page,
     * as there is no unique ID available.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].webPage == newList[newItemPosition].webPage
    }

    /**
     * Checks whether the contents of two items are the same.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}