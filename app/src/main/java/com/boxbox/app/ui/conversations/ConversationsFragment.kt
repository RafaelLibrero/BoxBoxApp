package com.boxbox.app.ui.conversations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boxbox.app.databinding.FragmentConversationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConversationsFragment : Fragment() {

    companion object {
        const val TOPIC_ID = "topic_id"
    }

    private val conversationsViewModel by viewModels<ConversationsViewModel>()

    private var _binding: FragmentConversationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConversationsBinding.inflate(layoutInflater, container, false)
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
                conversationsViewModel.state.collect { state ->
                    when (state) {
                        is ConversationsState.Error -> TODO()
                        is ConversationsState.Loading -> TODO()
                        is ConversationsState.Success -> TODO()
                    }
                }
            }
        }
    }

}