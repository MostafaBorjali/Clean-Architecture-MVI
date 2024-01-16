package com.borjali.presentation.ui.splash

import androidx.lifecycle.lifecycleScope
import com.borjali.presentation.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint
import com.borjali.domain.Constants.Companion.IS_LOGGED_IN
import com.borjali.domain.Constants.Companion.SPLASH_DELAY
import com.borjali.domain.preference.CleanAppPreferences
import com.borjali.presentation.R
import com.borjali.presentation.databinding.FragmentSplashBinding
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, MainViewModel>(FragmentSplashBinding::inflate) {

    @Inject
    lateinit var preferences: CleanAppPreferences
    override fun subscribeObservers() {
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            navigate(R.id.action_from_splash_fragment_to_worker_fragment)
          /*  if (preferences.getBool(IS_LOGGED_IN))
                navigate(R.id.action_from_splash_fragment_to_worker_fragment)
            else
                navigate(R.id.action_from_splash_fragment_to_login_fragment)*/
        }
    }
}
