package com.boxbox.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.registerStepContainer) == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.registerStepContainer, RegisterStep1Fragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToNextStep() {
        // Aquí iría la lógica para cambiar al siguiente fragmento (por ejemplo StepTwoFragment)
        // childFragmentManager.beginTransaction()
        //     .replace(R.id.registerStepContainer, StepTwoFragment())
        //     .addToBackStack(null)
        //     .commit()
    }

    fun goToPreviousStep() {
        childFragmentManager.popBackStack()
    }
}