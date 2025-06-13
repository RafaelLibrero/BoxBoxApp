package com.boxbox.app.ui.profileEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.crossfade
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentProfileBinding
import com.boxbox.app.databinding.FragmentProfileEditBinding
import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.User
import com.boxbox.app.ui.profile.ProfileState
import com.boxbox.app.utils.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {

    private val profileEditViewModel by viewModels<ProfileEditViewModel>()

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileEditBinding.inflate(layoutInflater, container, false)
        initUI()
        profileEditViewModel.getProfile()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileEditViewModel.state.collect { state ->
                    when (state) {
                        is ProfileEditState.Error -> errorState(state)
                        is ProfileEditState.Loading -> loadingState()
                        is ProfileEditState.Success -> successState(state)
                        is ProfileEditState.Ready -> readyState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun readyState(state: ProfileEditState.Ready) {
        val user: User = state.user
        val teams: List<Team>? = state.teams
        val drivers: List<Driver>? = state.drivers

        with(binding) {
            progressBar.visibility = View.GONE
            ivProfile.load(user.profilePicture) { crossfade(true) }
            etName.setText(user.userName)
            etBiography.setText(user.biography)

            if (!teams.isNullOrEmpty()) {
                val teamNames = teams.map { it.teamName }
                val teamAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, teamNames)
                teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerTeam.adapter = teamAdapter

                val selectedTeamIndex = teams.indexOfFirst { it.teamID == user.teamId }
                if (selectedTeamIndex >= 0) {
                    spinnerTeam.setSelection(selectedTeamIndex)
                }
            }

            if (!drivers.isNullOrEmpty()) {
                val driverNames = drivers.map { it.driverName }
                val driverAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, driverNames)
                driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDriver.adapter = driverAdapter

                val selectedDriverIndex = drivers.indexOfFirst { it.driverID == user.driverId }
                if (selectedDriverIndex >= 0) {
                    spinnerDriver.setSelection(selectedDriverIndex)
                }
            }
        }
    }

    private fun successState(state: ProfileEditState.Success) {

    }

    private fun errorState(state: ProfileEditState.Error) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }
}