package com.boxbox.app.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil3.load
import coil3.transform.CircleCropTransformation
import coil3.request.crossfade
import coil3.request.transformations
import com.boxbox.app.R
import com.boxbox.app.data.workers.TokenRefreshWorker
import com.boxbox.app.databinding.ActivityMainBinding
import com.boxbox.app.ui.auth.AuthState
import com.boxbox.app.ui.auth.AuthViewModel
import com.boxbox.app.ui.auth.login.LoginDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Duration

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val authViewModel: AuthViewModel by viewModels()

    private val fragmentsRequireLogin = setOf(
        R.id.profileFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.authState.collect { authState ->
                    when (authState) {
                        is AuthState.Authenticated -> {
                            invalidateOptionsMenu()
                            updateToolbarIcons(authState)
                            checkAndRefreshTokenIfNeeded()
                            scheduleTokenRefreshWorker()
                        }
                        is AuthState.Unauthenticated -> {
                            invalidateOptionsMenu()
                            updateToolbarIcons(authState)
                            cancelTokenRefreshWorker()
                        }
                    }
                }
            }
        }

        initUI()
    }

    private fun updateToolbarIcons(authState: AuthState) {
        val profileIcon = binding.toolbar.findViewById<ImageView>(R.id.image_profile)
        val loginIcon = binding.toolbar.findViewById<ImageView>(R.id.image_login)

        when (authState) {
            is AuthState.Authenticated -> {
                loginIcon.visibility = View.GONE
                profileIcon.visibility = View.VISIBLE

                lifecycleScope.launch {
                    val imageUrl = authViewModel.fetchUserImageUrlFromApi()
                    profileIcon.load(imageUrl) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                }

                profileIcon.setOnClickListener {
                    showPopupMenu(it)
                }
            }

            is AuthState.Unauthenticated -> {
                profileIcon.visibility = View.GONE
                loginIcon.visibility = View.VISIBLE

                loginIcon.setOnClickListener {
                    LoginDialogFragment().show(supportFragmentManager, "loginDialog")
                }
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)

        menuInflater.inflate(R.menu.toolbar_popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.settings -> {
                    true
                }
                R.id.logout -> {
                    handleLogout()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun handleLogout() {
        lifecycleScope.launch {
            authViewModel.logout()

            val currentFragmentId = navController.currentDestination?.id

            if (currentFragmentId != null && currentFragmentId in fragmentsRequireLogin) {
                navController.popBackStack(navController.graph.startDestinationId, false)
            }
        }
    }

    private fun initUI() {
        setSupportActionBar(binding.toolbar)
        initNavigation()
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val showBackButton = when (destination.id) {
                R.id.conversationsFragment,
                R.id.postsFragment,
                R.id.profileFragment -> true
                else -> false
            }

            supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
            supportActionBar?.setHomeButtonEnabled(showBackButton)

            val upArrow = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_back)
            upArrow?.setTint(resources.getColor(R.color.onPrimary, theme))
            supportActionBar?.setHomeAsUpIndicator(upArrow)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun scheduleTokenRefreshWorker() {
        val workRequest = PeriodicWorkRequestBuilder<TokenRefreshWorker>(
            Duration.ofMinutes(15),
            Duration.ofMinutes(5)
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "token_refresh_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun cancelTokenRefreshWorker() {
        WorkManager.getInstance(this).cancelUniqueWork("token_refresh_work")
    }

    private suspend fun checkAndRefreshTokenIfNeeded() {
        val token = authViewModel.getToken()
        if (token != null && authViewModel.isTokenExpiringSoon(token)) {
            val success = authViewModel.refreshToken()
            if (!success) {
                authViewModel.logout()
            }
        }
    }
}