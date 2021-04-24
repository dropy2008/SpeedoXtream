package com.stanica.speedoxtream.ui.search

import androidx.leanback.app.SearchSupportFragment
import com.stanica.speedoxtream.R
import com.zaclimon.xipl.ui.search.ProviderSearchActivity

/**
 * Concrete implementation of a [ProviderSearchActivity] for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamProviderSearchActivity : ProviderSearchActivity() {

    override fun getSearchFragment(): SearchSupportFragment {
        return SpeedoXtreamProviderSearchFragment()
    }

    override fun getThemeId(): Int {
        return R.style.TvTheme
    }

}