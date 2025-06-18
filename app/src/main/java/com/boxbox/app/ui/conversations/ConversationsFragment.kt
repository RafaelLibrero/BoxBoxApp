package com.boxbox.app.ui.conversations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentConversationsBinding
import com.boxbox.app.ui.conversations.adapter.ConversationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class ConversationsFragment : Fragment() {

    private val conversationsViewModel by viewModels<ConversationsViewModel>()

    private lateinit var conversationsAdapter: ConversationsAdapter
    private var _binding: FragmentConversationsBinding? = null
    private val binding get() = _binding!!

    private var topicId by Delegates.notNull<Int>()
    private lateinit var topicTitle: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConversationsBinding.inflate(layoutInflater, container, false)
        topicId = requireArguments().getInt("topic_id")
        initUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        conversationsViewModel.getPagedConversations(topicId)
        setupRecyclerView()
        observePagingData()
        observeAdapterLoadState()
    }

    private fun setupRecyclerView() {
        conversationsAdapter = ConversationsAdapter { conversationId ->
            val bundle = Bundle().apply {
                putInt("conversation_id", conversationId)
            }
            findNavController().navigate(R.id.postsFragment, bundle)
        }

        binding.rvConversations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = conversationsAdapter
        }
    }

    private fun observePagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                conversationsViewModel.getPagedConversations(topicId).collectLatest { pagingData ->
                    conversationsAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun observeAdapterLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                conversationsAdapter.loadStateFlow.collect { loadState ->
                    val isLoading = loadState.refresh is LoadState.Loading
                    val isError = loadState.refresh is LoadState.Error

                    binding.progressBar.isVisible = isLoading
                    binding.rvConversations.isVisible = !isLoading && !isError

                    if (isError) {
                        val error = (loadState.refresh as LoadState.Error).error
                        Toast.makeText(requireContext(), error.message ?: "Error loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}