package com.android.universities.module_a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.universities.common.util.Result
import com.android.universities.module_a.adapter.UniversityAdapter
import com.android.universities.module_a.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Indicates Fragment as an entry point for Hilt dependency injection.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    // Initializes a ListViewModel instance, scoped to this activity's lifecycle.
    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding
    private var adapter: UniversityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        init()

        setObservers()

        return binding.root
    }

    private fun init() {
        // Initialize UniversityAdapter with empty list and assign it to the RecyclerView.
        adapter = UniversityAdapter(emptyList())
        binding.rvUniversities.adapter = adapter
    }

    private fun setObservers() {
        // Observe List<University> LiveData.
        viewModel.universities.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    // Display University list, hide error message view and loading progress bar.
                    binding.apply {
                        rvUniversities.isVisible = true
                        tvErrorMessage.isVisible = false
                        binding.progressBar.hide()
                    }

                    result.data?.let { universities ->
                        // Refresh the list of universities with new data.
                        adapter?.refreshList(universities)
                    }
                }

                Result.Status.ERROR -> {
                    // Set and display error message, hide RecyclerView and loading progress bar.
                    binding.apply {
                        tvErrorMessage.text = result.message ?: "Something went wrong"

                        rvUniversities.isVisible = false
                        tvErrorMessage.isVisible = true
                        binding.progressBar.hide()
                    }
                }

                Result.Status.LOADING -> {
                    // Display loading progress bar, hide RecyclerView and error message view.
                    binding.apply {
                        rvUniversities.isVisible = false
                        tvErrorMessage.isVisible = false
                        progressBar.show()
                    }
                }
            }
        }
    }
}