package com.stanica.speedoxtream.ui.auth

import com.stanica.speedoxtream.repository.SharedPreferencesRepository

/**
 * Concrete implementation of the AuthPresenter interface for the signing up step of the
 * authentication process.
 *
 * @property authView the [AuthView] used to callback depending on the authentication results.
 *
 * @author stanica
 * @date  11/04/2021
 */
class AuthPresenterImpl(view: AuthView) : AuthPresenter {

    /*
     In order to simplify testing between components, Presenters should not have any dependencies
     to com.android.* classes. In that case, let's reuse interfaces that should technically avoid
     any dependencies with them.
     */

    private val authView = view

    override fun validateInfo(url: String, sharedPreferencesRepository: SharedPreferencesRepository) {
        val asyncAuthValidateInfo = AsyncAuthValidateInfo(url, authView, sharedPreferencesRepository)
        asyncAuthValidateInfo.execute()
    }

}