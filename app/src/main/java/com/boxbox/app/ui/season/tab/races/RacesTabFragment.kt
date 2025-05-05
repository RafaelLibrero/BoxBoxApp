package com.boxbox.app.ui.season.tab.races

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
import com.boxbox.app.databinding.FragmentRacesTabBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RacesTabFragment : Fragment() {

    private val racesViewModel by viewModels<RacesViewModel>()

    private lateinit var racesAdapter: RacesAdapter
    private var _binding: FragmentRacesTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRacesTabBinding.inflate(layoutInflater, container, false)
        racesViewModel.getRaces()
        initUI()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUI() {
        racesAdapter = RacesAdapter()
        binding.rvRaces.apply {
            adapter = racesAdapter
            layoutManager = LinearLayoutManager(context)
        }
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                racesViewModel.state.collect { state ->
                    when (state) {
                        is RacesState.Loading -> loadingState()
                        is RacesState.Success -> successState(state)
                        is RacesState.Error -> errorState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvRaces.visibility = View.GONE
    }

    private fun successState(state: RacesState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.rvRaces.visibility = View.VISIBLE
        racesAdapter.updateList(state.races)
    }

    private fun errorState(state: RacesState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvRaces.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }
}