package com.boxbox.app.ui.conversations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentConversationsBinding
import com.boxbox.app.ui.conversations.adapter.ConversationsAdapter
import dagger.hilt.android.AndroidEntryPoint
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
        conversationsViewModel.loadData(1, topicId)
        conversationsAdapter = ConversationsAdapter { onItemSelected(it) }
        binding.rvConversations.apply {
            adapter = conversationsAdapter
            layoutManager = LinearLayoutManager(context)
        }


        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                conversationsViewModel.state.collect { state ->
                    when (state) {
                        is ConversationsState.Error -> errorState(state)
                        is ConversationsState.Loading -> loadingState()
                        is ConversationsState.Success -> successState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvConversations.visibility = View.GONE
    }

    private fun successState(state: ConversationsState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.rvConversations.visibility = View.VISIBLE
        topicTitle = state.topic.title
        (requireActivity() as AppCompatActivity).supportActionBar?.title = topicTitle
        conversationsAdapter.updateList(state.conversations)
    }

    private fun errorState(state: ConversationsState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvConversations.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    private fun onItemSelected(conversationId: Int) {
        val bundle = Bundle().apply {
            putInt("conversation_id", conversationId)
        }

        findNavController().navigate(
            R.id.postsFragment,
            bundle
        )
    }

}