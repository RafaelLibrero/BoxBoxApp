package com.boxbox.app.ui.season.tab.teams

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
import com.boxbox.app.databinding.FragmentTeamsTabBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamsTabFragment : Fragment() {

    private val teamsViewModel by viewModels<TeamsViewModel>()

    private lateinit var teamsAdapter: TeamsAdapter
    private var _binding: FragmentTeamsTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamsTabBinding.inflate(layoutInflater, container, false)
        initUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        teamsViewModel.getTeams()
        teamsAdapter = TeamsAdapter()
        binding.rvTeams.apply {
            adapter = teamsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                teamsViewModel.state.collect { state ->
                    when (state) {
                        is TeamsState.Loading -> loadingState()
                        is TeamsState.Success -> successState(state)
                        is TeamsState.Error -> errorState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvTeams.visibility = View.GONE
    }

    private fun successState(state: TeamsState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.rvTeams.visibility = View.VISIBLE
        teamsAdapter.updateList(state.teams)
    }

    private fun errorState(state: TeamsState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvTeams.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }
}