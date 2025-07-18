package com.boxbox.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentRegisterStep2Binding
import kotlin.getValue

class RegisterStep2Fragment: Fragment() {

    private var _binding: FragmentRegisterStep2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNextStep.setOnClickListener {
            val fullName = binding.edtFullName.text.toString().trim()

            if (fullName.isEmpty()) {
                binding.tilFullName.error = getString(R.string.required_field)
                return@setOnClickListener
            }

            viewModel.setStepTwoData(
                fullName = fullName,
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}