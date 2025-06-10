package com.boxbox.app.ui.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentProfileBinding
import com.boxbox.app.domain.model.User
import com.boxbox.app.utils.DateFormatter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel by viewModels<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        initUI()
        profileViewModel.getProfile()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Perfil"
        initUIState()
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
        val user: User = state.user

        with(binding) {
            progressBar.visibility = View.GONE
            Picasso.get().load(user.profilePicture).into(ivProfile)
            tvName.text = user.userName
            tvBiography.text = user.biography
            tvRegistrationDate.text = getString(
                R.string.registered_on,
                DateFormatter.formatDate(user.registrationDate)
            )
            tvLastAccess.text = getString(
                R.string.last_access,
                DateFormatter.getLastAccessText(requireContext(), user.lastAccess)
            )
            tvPosts.text = getString(
                R.string.posts_number,
                user.totalPosts
            )
        }
    }

    private fun errorState(state: ProfileState.Error) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }
}