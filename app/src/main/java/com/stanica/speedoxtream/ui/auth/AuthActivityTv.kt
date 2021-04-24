package com.stanica.speedoxtream.ui.auth

import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.fragment.app.FragmentActivity
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.util.ActivityUtil

/**
 * Activity class used to start an authentication (sign-in) process for
 * a SpeedoXtream user.
 *
 * @author stanica
 * @date  11/04/2021
 */
class AuthActivityTv : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityUtil.isTvMode(this)) {
            setTheme(R.style.TvTheme)
            GuidedStepSupportFragment.addAsRoot(this, WelcomeGuidedFragment(), android.R.id.content)
        }
    }

    override fun onBackPressed() {
        // Similar to the Leanback showcase, exit the Activity if we're connected.
        val guidedStepSupportFragment = GuidedStepSupportFragment.getCurrentGuidedStepSupportFragment(supportFragmentManager)

        guidedStepSupportFragment?.let {
            if (it is UserConnectedGuidedFragment) {
                setResult(androidx.fragment.app.FragmentActivity.RESULT_OK)
                finish()
            } else if (it is WelcomeGuidedFragment) {
                setResult(androidx.fragment.app.FragmentActivity.RESULT_CANCELED)
            }
            super.onBackPressed()
        }
    }
}