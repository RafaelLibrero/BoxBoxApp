package com.boxbox.app.ui.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentProfileBinding
import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.User
import com.boxbox.app.ui.auth.AuthViewModel
import com.boxbox.app.utils.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private val authViewModel: AuthViewModel by activityViewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Perfil"
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.profileEditFragment)
        }
        val userId = arguments?.getInt("userId", -1)
        if (userId != null && userId != -1) {
            profileViewModel.getProfile(userId)
        } else {
            profileViewModel.getProfile()
        }
        initUIState()
        handleLogout()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.state.collect { state ->
                    when (state) {
                        is ProfileState.Error -> errorState(state)
                        is ProfileState.Loading -> loadingState()
                        is ProfileState.Success -> successState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun successState(state: ProfileState.Success) {
        val user: User = state.data.user
        val team: Team? = state.data.team
        val driver: Driver? = state.data.driver

        with(binding) {
            progressBar.visibility = View.GONE
            ivProfile.load(user.profilePicture) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvName.text = user.userName
            tvBiography.text = user.biography
            tvRegistrationDate.text = DateFormatter.formatToLongDate(user.registrationDate!!)
            tvLastAccess.text = DateFormatter.getLastAccessText(requireContext(), user.lastAccess!!)
            tvPosts.text = user.totalPosts.toString()
            if (team != null) {
                tvTeam.visibility = View.VISIBLE
                tvTeam.text = team.teamName
            } else {
                tvTeam.visibility = View.GONE
            }

            if (driver != null) {
                tvDriver.visibility = View.VISIBLE
                tvDriver.text = driver.driverName
            } else {
                tvDriver.visibility = View.GONE
            }
        }
    }

    private fun errorState(state: ProfileState.Error) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLogout() {
        binding.cvLogout.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.logout()
                findNavController().navigate(R.id.action_global_homeGraph)
            }
        }
    }
}