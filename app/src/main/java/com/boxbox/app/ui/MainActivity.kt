package com.boxbox.app.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil3.load
import coil3.transform.CircleCropTransformation
import coil3.request.crossfade
import coil3.request.transformations
import com.boxbox.app.R
import com.boxbox.app.databinding.ActivityMainBinding
import com.boxbox.app.ui.auth.AuthState
import com.boxbox.app.ui.auth.AuthViewModel
import com.boxbox.app.ui.auth.login.LoginDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = false
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.md_theme_surface)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.authState.collect { authState ->
                    when (authState) {
                        is AuthState.Authenticated -> {
                            invalidateOptionsMenu()
                            updateToolbarIcons(authState)
                        }
                        is AuthState.Unauthenticated -> {
                            invalidateOptionsMenu()
                            updateToolbarIcons(authState)
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
                    navController.navigate(R.id.profileFragment)
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

    private fun initUI() {
        setupToolbar()
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
            upArrow?.setTint(ContextCompat.getColor(this, android.R.color.white))
            supportActionBar?.setHomeAsUpIndicator(upArrow)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val toolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height)
            val totalHeight = statusBarHeight + toolbarHeight

            binding.appBar.layoutParams.height = totalHeight
            binding.appBar.requestLayout()

            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}