package com.borjali.presentation.ui.login

import android.os.Bundle
import android.view.View
import com.borjali.domain.model.event.EventErrorInternetConnection
import com.borjali.domain.model.user.sealed.LoginErrorType
import com.borjali.domain.model.user.sealed.LoginErrorType.EmailFieldHasError
import com.borjali.domain.model.user.sealed.LoginErrorType.EmailFieldNotMatch
import com.borjali.domain.model.user.sealed.LoginErrorType.PasswordFieldHasError
import com.borjali.domain.model.user.sealed.LoginErrorType.PasswordFieldLengthError
import com.borjali.domain.model.user.sealed.LoginErrorType.Success
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.domain.viewstate.StateOfView
import com.borjali.presentation.R
import com.borjali.presentation.databinding.FragmentLoginBinding
import com.borjali.presentation.extensions.isOnline
import com.borjali.presentation.extensions.navigate
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.login.LoginEventState.ForgetPasswordEvent
import com.borjali.presentation.ui.login.LoginEventState.LoginEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    // private var fcmToken: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signIn.setOnClickListener { login() }
        binding.forgetPassword.setOnClickListener { forgetPassword() }
    }

    /**
     * handle ui for show forget password view
     */
    private fun forgetPassword() {
        if (binding.forgetPassword.text == getString(R.string.forget_password)) {
            changeToForgetPasswordView()
        } else {
            changeToLoginView()
        }
    }

    /**
     * Start listening to Data State Event
     */
    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            dataStateChangeListener?.onDataStateChangeListener(dataState)
            when (dataState) {
                is DataState.SUCCESS -> handleStateOfView(dataState.stateOfView)
                is DataState.LOADING -> binding.signIn.setLoading(dataState.loading)
                is DataState.ERROR -> handelErrorField(dataState.data?.errorType)
                else -> {}
            }
        }
    }

    /**
     * handle ui state behavior
     */
    private fun handleStateOfView(stateOfView: StateOfView?) {
        when (stateOfView) {
            is StateOfView.UserLogin -> navigate(R.id.worker_fragment)
            is StateOfView.ForgetPassword -> changeToLoginView()
            else -> {}
        }
    }

    /**
     * handle ui login error validation
     */
    private fun handelErrorField(errorType: LoginErrorType?) {
        when (errorType) {
            is EmailFieldHasError -> {
                binding.inputEmailSignIn.error(getString(R.string.error_empty_email))
            }

            is EmailFieldNotMatch -> {
                binding.inputEmailSignIn.error(getString(R.string.error_structure_email))
            }

            is PasswordFieldHasError -> {
                binding.inputPasswordSignIn.error(getString(R.string.error_password_empty))
            }

            is PasswordFieldLengthError -> {
                binding.inputPasswordSignIn.error(getString(R.string.error_password_length))
            }

            is Success -> {}
            else -> {}
        }
    }

    /**
     * set event for login
     */
    private fun login() {
        if (requireContext().isOnline)
            viewModel.setEventState(
                if (binding.signIn.getText() == getString(R.string.login))
                    LoginEvent(
                        binding.inputEmailSignIn.getInputText(),
                        binding.inputPasswordSignIn.getInputText()
                    )
                else
                    ForgetPasswordEvent(binding.inputEmailSignIn.getInputText())
            )
        else
            EventOfCleanApp.send(EventErrorInternetConnection)
    }

    /**
     * handle ui for show login view
     */
    private fun changeToLoginView() {
        binding.signIn.setText(getString(R.string.login))
        binding.forgetPassword.text = getString(R.string.forget_password)
        binding.inputPasswordSignIn.visibility = View.VISIBLE
        clearText()
    }

    /**
     * handle ui for show forget password view
     */
    private fun changeToForgetPasswordView() {
        binding.signIn.setText(getString(R.string.forget_password_button_text))
        binding.forgetPassword.text = getString(R.string.login)
        binding.inputPasswordSignIn.visibility = View.GONE
        clearText()
    }

    /**
     * clear text for all input in ui
     */
    private fun clearText() {
        binding.inputPasswordSignIn.clear()
        binding.inputEmailSignIn.clear()
    }
}
