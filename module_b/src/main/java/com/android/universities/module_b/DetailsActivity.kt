package com.android.universities.module_b

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
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

        init()

        readIntentData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the toolbar's navigation back.
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        // Set up the toolbar.
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun readIntentData() {
        intent?.let {
            // Retrieve the UniversityDetails object from the intent extras.
            val university: UniversityDetails? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelableExtra(EXTRA_UNIVERSITY_DETAILS, UniversityDetails::class.java)
                } else {
                    it.getParcelableExtra(EXTRA_UNIVERSITY_DETAILS)
                }

            // Bind the retrieved UniversityDetails object to the UI.
            binding.university = university
        }
    }
}