package com.android.universities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.universities.adapter.UniversityAdapter
import com.android.universities.data.University
import com.android.universities.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Indicates Activity as an entry point for Hilt dependency injection.
 */
@AndroidEntryPoint
class ListActivity : AppCompatActivity(), UniversityAdapter.EventListener {
    // Initializes a ListViewModel instance, scoped to this activity's lifecycle.
    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: ActivityListBinding
    private var adapter: UniversityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        setObservers()
    }

    override fun onItemClicked(university: University) {

    }

    private fun init() {
        // Initialize UniversityAdapter with empty list and assign it to the RecyclerView.
        adapter = UniversityAdapter(emptyList(), this)
        binding.rvUniversities.adapter = adapter
    }

    private fun setObservers() {
        // Observe List<University> LiveData.
        viewModel.universities.observe(this) { result ->
            when (result.status) {
                com.android.universities.util.Result.Status.SUCCESS -> {
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

                com.android.universities.util.Result.Status.ERROR -> {
                    // Set and display error message, hide RecyclerView and loading progress bar.
                    binding.apply {
                        tvErrorMessage.text = result.message ?: "Something went wrong"

                        rvUniversities.isVisible = false
                        tvErrorMessage.isVisible = true
                        binding.progressBar.hide()
                    }
                }

                com.android.universities.util.Result.Status.LOADING -> {
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