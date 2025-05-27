package com.boxbox.app.ui.posts

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
import com.boxbox.app.databinding.FragmentPostsBinding
import com.boxbox.app.ui.posts.adapter.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val postsViewModel by viewModels<PostsViewModel>()

    private lateinit var postsAdapter: PostsAdapter
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private var conversationId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        conversationId = requireArguments().getInt("conversation_id")
        initUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        postsViewModel.getPosts(1, conversationId)
        postsAdapter = PostsAdapter()
        binding.rvPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postsViewModel.state.collect { state ->
                    when (state) {
                        is PostsState.Error -> errorState(state)
                        is PostsState.Loading -> loadingState()
                        is PostsState.Success -> successState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvPosts.visibility = View.GONE
    }

    private fun successState(state: PostsState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.rvPosts.visibility = View.VISIBLE
        postsAdapter.updateList(state.posts)
    }

    private fun errorState(state: PostsState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvPosts.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }
}