package com.android.universities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.universities.databinding.ActivityMainBinding
import com.android.universities.module_a.ListFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Indicates Activity as an entry point for Hilt dependency injection.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }
    }
}