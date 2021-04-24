package com.stanica.speedoxtream.ui.settings.userinfo

import android.content.Context
import android.os.Bundle
import androidx.leanback.app.ErrorSupportFragment
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import android.view.View
import android.view.ViewGroup
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.util.Constants
import java.util.*

/**
 * Fragment used to show the current user's account status.
 *
 * @author stanica
 * @date  11/04/2021
 */
class UserInfoGuidedFragment : GuidedStepSupportFragment(), UserInfoView {

    private lateinit var userProgressBarManager: ProgressBarManager

    override fun onStart() {
        super.onStart()

        guidanceStylist.apply {
            // Don't show the guidance stuff for the moment and show the progress bar
            titleView.visibility = View.INVISIBLE
            descriptionView.visibility = View.INVISIBLE
            breadcrumbView.visibility = View.INVISIBLE
        }

        userProgressBarManager = ProgressBarManager().apply {
            setRootView(activity?.findViewById(android.R.id.content) as ViewGroup)
            initialDelay = 250
            show()
        }

        val sharedPreferences = activity?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val url = sharedPreferences?.getString(Constants.PROVIDER_URL_PREFERENCE, "")
        val username = sharedPreferences?.getString(Constants.USERNAME_PREFERENCE, "")
        val password = sharedPreferences?.getString(Constants.PASSWORD_PREFERENCE, "")
        val endpoint = getString(R.string.provider_user_info_url, url, username, password)
        val userInfoPresenter = UserInfoPresenterImpl(endpoint, this)
        userInfoPresenter.retrieveUserInfo()
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val sharedPreferences = activity?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val title = getString(R.string.user_info_text)
        val breadcrumb = sharedPreferences?.getString(Constants.USERNAME_PREFERENCE, "")

        return GuidanceStylist.Guidance(title, null, breadcrumb, null)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val builder = GuidedAction.Builder(context).apply {
            clickAction(GuidedAction.ACTION_ID_OK)
        }.build()
        actions.add(builder)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {

        /*
        There seems to have an issue with popBackStackToGuidedStepFragment(), let's just use the
        classical popBackStack().
        */

        fragmentManager?.popBackStack()
    }

    override fun onConnectionFailed() {
        guidanceStylist?.apply {
            titleView.visibility = View.VISIBLE
            descriptionView.visibility = View.VISIBLE
            breadcrumbView.visibility = View.VISIBLE
            iconView.visibility = View.VISIBLE
            titleView.text = getString(R.string.error_text)
            descriptionView.text = getString(R.string.user_info_not_accessible)
            iconView.setImageDrawable(context?.getDrawable(R.drawable.lb_ic_sad_cloud))
        }

        userProgressBarManager.hide()
    }

    override fun onConnectionSuccess(status: String, expirationDate: Date, isTrial: Boolean, maxConnections: Int) {

        guidanceStylist?.apply {
            val trial = if (isTrial) getString(R.string.yes_text) else getString(R.string.no_text)

            descriptionView.text = getString(R.string.user_info_description, status, expirationDate, trial, maxConnections.toString())
            titleView.visibility = View.VISIBLE
            descriptionView.visibility = View.VISIBLE
            breadcrumbView.visibility = View.VISIBLE
        }

        userProgressBarManager.hide()
    }

    override fun getUserInfoApiEndpoint(): String {
        val sharedPreferences = activity?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val url = sharedPreferences?.getString(Constants.PROVIDER_URL_PREFERENCE, "")
        val username = sharedPreferences?.getString(Constants.USERNAME_PREFERENCE, "")
        val password = sharedPreferences?.getString(Constants.PASSWORD_PREFERENCE, "")
        return getString(R.string.provider_user_info_url, url, username, password)
    }
}