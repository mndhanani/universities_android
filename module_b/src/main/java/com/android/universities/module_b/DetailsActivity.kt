package com.android.universities.module_b

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.universities.module_b.data.UniversityDetails
import com.android.universities.module_b.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_UNIVERSITY_DETAILS = "university_details"
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            val universityDetails: UniversityDetails? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelableExtra(EXTRA_UNIVERSITY_DETAILS, UniversityDetails::class.java)
                } else {
                    it.getParcelableExtra(EXTRA_UNIVERSITY_DETAILS)
                }
        }
    }
}