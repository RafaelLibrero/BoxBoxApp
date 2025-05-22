package com.boxbox.app.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boxbox.app.databinding.FragmentPostsBinding
import kotlinx.coroutines.launch

class PostsFragment : Fragment() {

    private val postsViewModel by viewModels<PostsViewModel>()

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        initUIState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postsViewModel.state.collect { state ->
                    when (state) {
                        is PostsState.Error -> TODO()
                        is PostsState.Loading -> TODO()
                        is PostsState.Success -> TODO()
                    }
                }
            }
        }
    }
}