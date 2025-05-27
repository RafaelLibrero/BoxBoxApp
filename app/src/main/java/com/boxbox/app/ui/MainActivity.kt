package com.boxbox.app.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
                        }
                        is AuthState.Unauthenticated -> {
                            invalidateOptionsMenu()
                        }
                    }
                }
            }
        }

        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        when (authViewModel.authState.value) {
            is AuthState.Authenticated -> {
                menuInflater.inflate(R.menu.toolbar_menu_authenticated, menu)
            }
            is AuthState.Unauthenticated -> {
                menuInflater.inflate(R.menu.toolbar_menu_unauthenticated, menu)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnLogin -> {
                LoginDialogFragment().show(supportFragmentManager, "loginDialog")
                true
            }
            R.id.profile -> {
                showPopupMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(this, findViewById(R.id.profile)) // `findViewById(R.id.toolbar)` es la referencia de tu Toolbar

        menuInflater.inflate(R.menu.toolbar_popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.settings -> {
                    // Acción para la configuración
                    true
                }
                R.id.logout -> {
                    authViewModel.clearToken()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
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
    }
}