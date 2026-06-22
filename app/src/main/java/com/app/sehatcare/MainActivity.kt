package com.app.sehatcare

import com.app.sehatcare.base.BaseActivity
import com.app.sehatcare.common.bus.LoginEventBus
import com.app.sehatcare.common.bus.SessionEvent
import com.app.sehatcare.common.extensions.collectLifecycleFlowActivity
import com.app.sehatcare.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject lateinit var loginEventBus: LoginEventBus

    override fun setupViews() {
    }

    override fun observeData() {
        loginEventBus.events.collectLifecycleFlowActivity(this) { event ->
            when (event) {
                is SessionEvent.LoggedOut, is SessionEvent.SessionExpired -> {
                    // TODO once you add a real auth flow + nav graph destinations:
                    // val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
                    // navHost.navController.navigate(R.id.authFragment) // popUpTo to clear back stack
                }
                is SessionEvent.LoggedIn -> Unit
            }
        }
    }
}
