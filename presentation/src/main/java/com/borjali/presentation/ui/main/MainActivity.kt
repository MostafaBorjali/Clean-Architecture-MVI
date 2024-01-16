package com.borjali.presentation.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import com.borjali.domain.Constants.Companion.ENGLISH
import com.borjali.domain.Constants.Companion.GERMANY
import com.borjali.domain.Constants.Companion.LOCALE
import com.borjali.domain.model.base.CurrentLanguage
import com.borjali.domain.model.event.EventErrorInternetConnection
import com.borjali.domain.model.event.EventSignOut
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.domain.utils.MessageType
import com.borjali.domain.viewstate.StateOfView.TokenSignOut
import com.borjali.domain.viewstate.StateOfView.UserLogOut
import com.borjali.presentation.R
import com.borjali.presentation.databinding.ActivityMainBinding
import com.borjali.presentation.extensions.displaySnackBar
import com.borjali.presentation.extensions.isOnline
import com.borjali.presentation.ui.base.BaseActivity
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var popupMenu: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        locale()
        subscribeObservers()
        backHandle()
    }

    private fun locale() {
        val language = preferences.getString(LOCALE, GERMANY)
        binding.changeLocale.text = language.uppercase(Locale.getDefault())
        binding.changeLocale.setOnClickListener {
            if (language == GERMANY){
                preferences.setString(LOCALE, ENGLISH)
                CurrentLanguage.language = ENGLISH
            }else{
                preferences.setString(LOCALE, GERMANY)
                CurrentLanguage.language = GERMANY
            }
            val intent = intent
            finish()
            startActivity(intent)
        }
    }

    /**
     * Start listening to Data State Event
     */
    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.SUCCESS -> {
                    if (dataState.stateOfView is TokenSignOut || dataState.stateOfView is UserLogOut)
                        navController.navigate(R.id.splash_fragment)
                }

                is DataState.ERROR -> {
                    displaySnackBar(dataState.stateMessage?.message, MessageType.ERROR)
                }

                else -> {

                }
            }


        }
        /**
         * when refresh token encounter 401,
         * we post EventSignOut and listening here and log out manager
         */
        EventOfCleanApp.registerEvent(EventSignOut::class.java) {
            viewModel.setEventState(MainEventState.TokenSignOut(TokenSignOut))
        }
        /**
         * when not found internet connection,
         * we post  EventErrorInternetConnection and listening here for manage this
         */
        EventOfCleanApp.registerEvent(EventErrorInternetConnection::class.java) {
            binding.internetErrorLayout.visibility = View.VISIBLE
        }
        binding.menuToolbar.setOnClickListener {
            popupMenu = showAlertLogOut()
            popupMenu?.showAsDropDown(
                binding.menuToolbar,
                -250,
                -150,
                Gravity.CENTER
            )
        }
        binding.internetErrorLayout.setOnClickListener { }
        binding.buttonRetry.setOnClickListener {
            if (this@MainActivity.isOnline)
                binding.internetErrorLayout.visibility = View.GONE
        }
    }

    /**
     * this function new way for behavior back button
     */
    private fun backHandle() {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navController.currentDestination?.id == R.id.login_fragment ||
                        navController.currentDestination?.id == R.id.worker_fragment
                    )
                        finish()
                    navController.popBackStack()
                }
            }
        )
    }

    /**
     * this function show log out dialog
     */
    private fun showLogOutDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.layout_dialog_log_out, null)
        val buttonYes = view.findViewById<Button>(R.id.button_yes)
        val buttonNo = view.findViewById<AppCompatButton>(R.id.button_no)
        builder.setView(view)
        buttonYes.setOnClickListener {
            viewModel.setEventState(MainEventState.LogOutUser(UserLogOut))
            builder.dismiss()
        }
        buttonNo.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    /**
     * this function setup navigation component and handle tool bar with back button view
     */
    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.login_fragment || destination.id == R.id.splash_fragment)
                binding.toolbar.visibility = View.GONE
            else {
                binding.toolbar.visibility = View.VISIBLE
                if (destination.id == R.id.worker_fragment) {
                    binding.backButton.visibility = View.GONE
                    binding.changeLocale.visibility = View.VISIBLE
                } else{
                    binding.backButton.visibility = View.VISIBLE
                    binding.changeLocale.visibility = View.GONE
                }
            }


        }
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    /**
     * this function show log out alert
     */
    @SuppressLint("InflateParams")
    private fun showAlertLogOut(
    ): PopupWindow {
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.layout_popup_view_toolbar, null)

        view.findViewById<TextView>(R.id.log_out).apply {

            setOnClickListener {
                if (this@MainActivity.isOnline)
                    showLogOutDialog()
                else
                    EventOfCleanApp.send(EventErrorInternetConnection)
                dismiss()
            }

        }


        return PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
    }

    /**
     * this function dismiss log out alert
     */
    private fun dismiss() {
        if (popupMenu?.isShowing == true) {
            popupMenu?.dismiss()
        }
    }
}
