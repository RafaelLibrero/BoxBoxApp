package com.boxbox.app.ui.season.tab.drivers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.app.databinding.FragmentDriversTabBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriversTabFragment : Fragment() {

    private val driversViewModel by viewModels<DriversViewModel>()

    private lateinit var driversAdapter: DriversAdapter
    private var _binding: FragmentDriversTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriversTabBinding.inflate(layoutInflater, container, false)
        initUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI(){
        driversViewModel.getDrivers()
        driversAdapter = DriversAdapter()
        binding.rvDrivers.apply {
            adapter = driversAdapter
            layoutManager = LinearLayoutManager(context)
        }
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                driversViewModel.state.collect { state ->
                    when (state) {
                        is DriversState.Loading -> loadingState()
                        is DriversState.Success -> successState(state)
                        is DriversState.Error -> errorState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvDrivers.visibility = View.GONE
    }

    private fun successState(state: DriversState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.rvDrivers.visibility = View.VISIBLE
        driversAdapter.updateList(state.drivers)
    }

    private fun errorState(state: DriversState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvDrivers.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }
}
