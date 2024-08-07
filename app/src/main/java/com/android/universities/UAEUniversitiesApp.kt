package com.android.universities

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Initializes Hilt and generates necessary components for dependency injection.
 */
@HiltAndroidApp
class UAEUniversitiesApp : Application()