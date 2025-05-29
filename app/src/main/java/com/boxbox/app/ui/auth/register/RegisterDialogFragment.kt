package com.boxbox.app.ui.auth.register

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentRegisterDialogBinding
import com.boxbox.app.ui.auth.login.LoginDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterDialogFragment : DialogFragment() {

    private val registerViewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDialogBinding.inflate(layoutInflater, container, false)

        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLoginClickable.setOnClickListener {
            dismiss()
            LoginDialogFragment().show(requireActivity().supportFragmentManager, "loginDialog")
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        register()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.state.collect { state ->
                    when (state) {
                        is RegisterState.Idle -> {}
                        is RegisterState.Loading -> {

                        }

                        is RegisterState.Success -> {
                            dismiss()
                            LoginDialogFragment().show(
                                requireActivity().supportFragmentManager,
                                "loginDialog"
                            )
                        }

                        is RegisterState.Error -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                            Log.e("Register", state.message)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (!validateUsername()) {
            isValid = false
        }

        if (!validateEmail()) {
            isValid = false
        }

        if (!validatePassword()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUsername(): Boolean {
        with(binding) {
            val username = edtUsername.text.toString().trim()

            if (username.isEmpty()) {
                tilUsername.error = getString(R.string.required_field)
                return false
            }

            tilUsername.error = null
            return true
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
            val repeatPassword = edtRepeatPassword.text.toString().trim()

            if (password.isEmpty()) {
                tilPassword.error = getString(R.string.required_field)
                return false
            }

            if (password.length < 12) {
                tilPassword.error = getString(R.string.password_characters_required)
                return false
            }

            if (!password.any { it.isUpperCase() }) {
                tilPassword.error = getString(R.string.password_minus_required)
                return false
            }

            if (!password.any { it.isLowerCase() }) {
                tilPassword.error = getString(R.string.password_mayus_required)
                return false
            }

            if (!password.any { it.isDigit() }) {
                tilPassword.error = getString(R.string.password_number_required)
                return false
            }

            if (password != repeatPassword) {
                tilRepeatPassword.error = getString(R.string.password_not_match)
                return false
            }

            tilPassword.error = null
            tilRepeatPassword.error = null
            return true
        }
    }

    private fun register() {
        with(binding) {
            btnRegister.setOnClickListener {
                if (validateForm()) {
                    registerViewModel.register(
                        edtUsername.text.toString(),
                        edtEmail.text.toString(),
                        edtPassword.text.toString()
                    )
                }
            }
        }
    }
}