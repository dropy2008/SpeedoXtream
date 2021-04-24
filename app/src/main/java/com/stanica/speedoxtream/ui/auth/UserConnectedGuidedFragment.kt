package com.stanica.speedoxtream.ui.auth

import android.app.Activity
import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.tvinputsetup.SpeedoXtreamTvInputSetupActivity

/**
 *  [GuidedStepSupportFragment] that confirms when the user has been able to connect to it's
 *  provider.
 *
 *  @author stanica
 *  @date  11/04/2021
 */
class UserConnectedGuidedFragment : GuidedStepSupportFragment() {

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.connection_successful_text)
        val componentName = activity?.callingActivity
        val username = arguments?.getString(UsernameInputGuidedFragment.ARGUMENT_USERNAME)
        val breadcrumb = getString(R.string.username_parameter_text, username)
        val description = if (componentName?.className == SpeedoXtreamTvInputSetupActivity::class.java.name) {
            getString(R.string.connected_description_live_channels)
        } else {
            getString(R.string.connected_description_standalone)
        }

        return (GuidanceStylist.Guidance(title, description, breadcrumb, null))
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val exitAction = GuidedAction.Builder(context).apply {
            title(R.string.lets_go_text)
            id(GuidedAction.ACTION_ID_OK)
        }.build()

        actions.add(exitAction)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        activity?.setResult(Activity.RESULT_OK)
        finishGuidedStepSupportFragments()
    }

}