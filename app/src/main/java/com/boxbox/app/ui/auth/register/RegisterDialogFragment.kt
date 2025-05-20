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

        binding.btnRegister.setOnClickListener {
            registerViewModel.register(
                binding.edtUsername.toString(),
                binding.edtEmail.toString(),
                binding.edtPassword.toString()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.state.collect { state ->
                    when (state) {
                        is RegisterState.Idle -> {}
                        is RegisterState.Loading -> {

                        }
                        is RegisterState.Success -> {
                            dismiss()
                            LoginDialogFragment().show(requireActivity().supportFragmentManager, "loginDialog")
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
}