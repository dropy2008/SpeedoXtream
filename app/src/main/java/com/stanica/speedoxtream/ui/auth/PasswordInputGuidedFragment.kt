package com.stanica.speedoxtream.ui.auth

import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.leanback.widget.GuidedActionsStylist
import android.text.InputType
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.components.guidedactionstylist.SpeedoXtreamGuidedActionStylist

/**
 * [GuidedStepSupportFragment] asking for the user it's provider password.
 *
 * @author stanica
 * @date  11/04/2021
 *
 *
 *
 */
class PasswordInputGuidedFragment : GuidedStepSupportFragment() {

    companion object {
        internal const val ACTION_PASSWORD : Long = 0
        internal const val ARGUMENT_PASSWORD = "password"
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.enter_password_title)
        return (GuidanceStylist.Guidance(title, null, null, null))
    }

    override fun onCreateActionsStylist(): GuidedActionsStylist {
        return SpeedoXtreamGuidedActionStylist()
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val passwordAction = GuidedAction.Builder(context).apply {
            title(R.string.password_text)
            editTitle("")
            editable(true)
            editInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            id(ACTION_PASSWORD)
        }.build()

        actions.add(passwordAction)
    }

    override fun onGuidedActionEditedAndProceed(action: GuidedAction?): Long {
        val actionId = action?.id

        if (actionId == UsernameInputGuidedFragment.ACTION_USERNAME) {
            val password = action.editTitle.toString()
            if (password.isNotEmpty()) {
                val fragment = VerificationGuidedFragment()
                val currentArguments = Bundle().apply {
                    putString(UrlInputGuidedFragment.ARGUMENT_URL, arguments?.getString(UrlInputGuidedFragment.ARGUMENT_URL))
                    putString(UsernameInputGuidedFragment.ARGUMENT_USERNAME, arguments?.getString(UsernameInputGuidedFragment.ARGUMENT_USERNAME))
                    putString(ARGUMENT_PASSWORD, password)
                }
                fragment.arguments = currentArguments
                add(fragmentManager, fragment)
            }
        }
        return (GuidedAction.ACTION_ID_CURRENT)
    }

}