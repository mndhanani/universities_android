package com.android.universities.module_b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.universities.module_b.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Indicates Fragment as an entry point for Hilt dependency injection.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        return binding.root
    }
}