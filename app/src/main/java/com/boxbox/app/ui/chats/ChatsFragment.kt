package com.boxbox.app.ui.chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.app.databinding.FragmentChatsBinding
import com.boxbox.app.ui.auth.AuthState
import com.boxbox.app.ui.auth.AuthViewModel
import com.boxbox.app.ui.auth.login.LoginDialogFragment
import com.boxbox.app.ui.chats.adapter.ChatsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class ChatsFragment : Fragment() {

    private val chatsViewModel by viewModels<ChatsViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    private lateinit var chatsAdapter: ChatsAdapter

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        initUIState()

        binding.btnLogin.setOnClickListener {
            LoginDialogFragment().show(parentFragmentManager, "loginDialog")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        chatsAdapter = ChatsAdapter { onItemSelected(it) }
        binding.rvChats.apply {
            adapter = chatsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var wasAuthenticated = false
                combine(
                    chatsViewModel.state,
                    authViewModel.authState
                ) { chatsState, authState ->
                    chatsState to authState
                }.collect { (chatsState, authState) ->
                    // Detectar transición a autenticado para llamar solo una vez a getUserChats
                    if (authState is AuthState.Authenticated && !wasAuthenticated) {
                        chatsViewModel.getUserChats()
                    }
                    wasAuthenticated = authState is AuthState.Authenticated
                    when (authState) {
                        is AuthState.Unauthenticated -> {
                            binding.notAuthenticatedContainer.visibility = View.VISIBLE
                            binding.rvChats.visibility = View.GONE
                            binding.emptyChatsContainer.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                        }
                        is AuthState.Authenticated -> {
                            binding.notAuthenticatedContainer.visibility = View.GONE
                            when (chatsState) {
                                is ChatsState.Loading -> loadingState()
                                is ChatsState.Success -> successState(chatsState)
                                is ChatsState.Error -> errorState(chatsState)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvChats.visibility = View.GONE
        binding.emptyChatsContainer.visibility = View.GONE
    }

    private fun successState(state: ChatsState.Success) {
        binding.progressBar.visibility = View.GONE
        if (state.chatsWithUsers.isEmpty()) {
            binding.emptyChatsContainer.visibility = View.VISIBLE
            binding.rvChats.visibility = View.GONE
        } else {
            binding.emptyChatsContainer.visibility = View.GONE
            binding.rvChats.visibility = View.VISIBLE
            chatsAdapter.updateList(state.chatsWithUsers)
        }
    }

    private fun errorState(state: ChatsState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvChats.visibility = View.GONE
        binding.emptyChatsContainer.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    private fun onItemSelected(id: Int) {
        val bundle = Bundle().apply {
            putInt("id", id)
        }

        // findNavController().navigate(
            TODO()
            // bundle
        // )
    }
}