package com.boxbox.app.ui.auth.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boxbox.app.databinding.FragmentLoginDialogBinding
import com.boxbox.app.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.activityViewModels
import com.boxbox.app.R
import com.boxbox.app.ui.auth.register.RegisterDialogFragment

@AndroidEntryPoint
class LoginDialogFragment : DialogFragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    private var _binding: FragmentLoginDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginDialogBinding.inflate(layoutInflater, container, false)

        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initUIState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect { state ->
                    when (state) {
                        is LoginState.Idle -> {}
                        is LoginState.Loading -> {
                            // Mostrar un spinner o algo similar
                        }
                        is LoginState.Success -> {
                            authViewModel.saveUserId(state.profile.userId)
                            dismiss()
                        }
                        is LoginState.TokenObtained -> {
                            authViewModel.saveToken(state.token)
                            loginViewModel.getProfile()
                        }
                        is LoginState.Error -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.tvRegisterClickable.setOnClickListener {
            dismiss()
            RegisterDialogFragment().show(requireActivity().supportFragmentManager, "registerDialog")
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        login()
    }

    private fun login() {
        with (binding) {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()

                var isValid = true

                if (!validateEmail()) {
                    isValid = false
                }

                if (!validatePassword()) {
                    isValid = false
                }

                if (isValid) {
                    loginViewModel.login(email, password)
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        with(binding) {
            val email = edtEmail.text.toString().trim()

            if (email.isEmpty()) {
                tilEmail.error = getString(R.string.required_field)
                return false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.error = getString(R.string.email_no_valid_format)
                return false
            }

            tilEmail.error = null
            return true
        }
    }

    private fun validatePassword(): Boolean {
        with(binding) {
            val password = edtPassword.text.toString().trim()

            if (password.isEmpty()) {
                tilPassword.error = getString(R.string.required_field)
                return false
            }

            tilPassword.error = null
            return true
        }
    }
}
