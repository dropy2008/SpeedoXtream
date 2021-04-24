package com.stanica.speedoxtream.ui.tvinputsetup

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.stanica.speedoxtream.R

/**
 * Activity that is used to configure the a provider's channels upon going to the
 * Live Channels application.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamTvInputSetupActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speedoxtream_tv_input_setup)
    }

}