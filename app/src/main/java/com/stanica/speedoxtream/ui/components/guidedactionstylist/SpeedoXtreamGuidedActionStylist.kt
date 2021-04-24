package com.stanica.speedoxtream.ui.components.guidedactionstylist

import androidx.leanback.widget.GuidedActionsStylist
import com.stanica.speedoxtream.R

/**
 * Custom [GuidedActionsStylist] adapted for SpeedoX theme elements.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamGuidedActionStylist : GuidedActionsStylist() {

    override fun onProvideItemLayoutId(): Int {
        return R.layout.speedoxtream_guidedactions_item
    }

}