package com.boxbox.app.ui.posts

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentPostsBinding
import com.boxbox.app.ui.auth.AuthState
import com.boxbox.app.ui.auth.AuthViewModel
import com.boxbox.app.ui.posts.adapter.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val postsViewModel by viewModels<PostsViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    private lateinit var postsAdapter: PostsAdapter
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private var conversationId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        conversationId = requireArguments().getInt("conversation_id")
        initUI()
    }

    override fun onStart() {
        super.onStart()
        postsViewModel.startAutoRefresh(1, conversationId)
    }

    override fun onStop() {
        super.onStop()
        postsViewModel.stopAutoRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        postsViewModel.getPosts(1, conversationId)
        postsAdapter = PostsAdapter { onUserSelected(it)}
        binding.rvPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.btnPublish.setOnClickListener {
            val content = binding.etNewPost.text.toString().trim()
            if (content.isNotEmpty()) {
                postsViewModel.createPost(content, 1, conversationId)
                binding.etNewPost.text.clear()
            } else {
                Toast.makeText(requireContext(), "El post no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    postsViewModel.state,
                    authViewModel.authState
                ) { postsState, authState ->
                    postsState to authState
                }.collect { (postsState, authState) ->
                    when (postsState) {
                        is PostsState.Error -> errorState(postsState)
                        is PostsState.Loading -> loadingState()
                        is PostsState.Success -> successState(postsState)
                    }
                    when (authState) {
                        is AuthState.Authenticated -> binding.newPostContainer.visibility = View.VISIBLE
                        is AuthState.Unauthenticated -> binding.newPostContainer.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.container.visibility = View.GONE
    }

    private fun successState(state: PostsState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.container.visibility = View.VISIBLE
        postsAdapter.updateList(state.posts)
    }

    private fun errorState(state: PostsState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvPosts.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    private fun onUserSelected(userId: Int) {
        val bundle = Bundle().apply {
            putInt("userId", userId)
        }

        findNavController().navigate(
            R.id.profilePublicFragment,
            bundle
        )
    }
}