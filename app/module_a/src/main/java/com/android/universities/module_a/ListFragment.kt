package com.android.universities.module_a

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.universities.common.util.LOG_TAG
import com.android.universities.common.util.Result
import com.android.universities.module_a.adapter.UniversityAdapter
import com.android.universities.module_a.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Indicates Fragment as an entry point for Hilt dependency injection.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        setObservers()

        return binding.root
    }

    private fun setObservers() {
        viewModel.universities.observe(viewLifecycleOwner) {
            Log.d(LOG_TAG, "Universities: ${it.data}")

            if (it.status == Result.Status.SUCCESS) {
                it.data?.let { universities ->
                    binding.rvUniversities.adapter = UniversityAdapter(universities)
                }
            }
        }
    }
}