package com.stanica.speedoxtream.ui.settings

import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.stanica.speedoxtream.BuildConfig
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.settings.userinfo.UserInfoGuidedFragment

/**
 * Fragment that shows the about section of the app the user information.
 *
 * @author stanica
 * @date  11/04/2021
 */
class AboutGuidedFragment : GuidedStepSupportFragment() {

    companion object {
        private const val ACTION_USER_INFO: Long = 0
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.app_name)
        val description = getString(R.string.version_text, BuildConfig.VERSION_NAME)

        return GuidanceStylist.Guidance(title, description, null, null)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val userInfoAction = GuidedAction.Builder(context).apply {
            title(R.string.user_info_text)
            id(ACTION_USER_INFO)
        }.build()

        val okAction = GuidedAction.Builder(context).apply {
            clickAction(GuidedAction.ACTION_ID_OK)
        }.build()

        actions.add(userInfoAction)
        actions.add(okAction)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        when (action?.id) {
            ACTION_USER_INFO -> add(fragmentManager, UserInfoGuidedFragment())
            else -> activity?.finish()
        }
    }
}