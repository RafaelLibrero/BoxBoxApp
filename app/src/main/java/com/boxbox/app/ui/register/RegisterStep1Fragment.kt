package com.boxbox.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentRegisterStep1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterStep1Fragment: Fragment() {

    private var _binding: FragmentRegisterStep1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            btnNextStep.setOnClickListener {
                if (validateForm()) {
                    viewModel.setStepOneData(
                        username = edtUsername.text.toString(),
                        email = edtEmail.text.toString(),
                        password = edtPassword.text.toString()
                    )

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.registerStepContainer, RegisterStep2Fragment())
                        .addToBackStack(null)
                        .commit()

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateForm(): Boolean {
        val username = binding.edtUsername.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString()
        val repeatPassword = binding.edtRepeatPassword.text.toString()

        var valid = true

        with(binding) {
            if (username.isEmpty()) {
                tilUsername.error = getString(R.string.required_field)
                valid = false
            } else {
                tilUsername.error = null
            }

            if (email.isEmpty()) {
                tilEmail.error = getString(R.string.required_field)
                valid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.error = getString(R.string.email_no_valid_format)
                valid = false
            } else {
                tilEmail.error = null
            }

            if (password.isEmpty()) {
                tilPassword.error = getString(R.string.required_field)
                valid = false
            } else if (password.length < 12) {
                tilPassword.error = getString(R.string.password_characters_required)
                valid = false
            } else if (password.any {it.isUpperCase()}) {
                tilPassword.error = getString(R.string.password_minus_required)
                valid = false
            } else if (!password.any { it.isLowerCase() }) {
                tilPassword.error = getString(R.string.password_mayus_required)
                valid = false
            } else if (!password.any { it.isDigit() }) {
                tilPassword.error = getString(R.string.password_number_required)
                valid = false
            } else {
                tilPassword.error = null
            }

            if (password != repeatPassword) {
                tilRepeatPassword.error = getString(R.string.password_not_match)
                valid = false
            } else {
                binding.tilRepeatPassword.error = null
            }
        }

        return valid
    }
}