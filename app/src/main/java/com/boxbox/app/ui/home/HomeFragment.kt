package com.boxbox.app.ui.home

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.app.R
import com.boxbox.app.databinding.FragmentHomeBinding
import com.boxbox.app.ui.home.adapter.TopicAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var topicAdapter: TopicAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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
        homeViewModel.getVTopics()
        topicAdapter = TopicAdapter { onItemSelected(it) }
        binding.rvTopics.apply {
            adapter = topicAdapter
            layoutManager = LinearLayoutManager(context)
        }
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.state.collect { state ->
                    when (state) {
                        is HomeState.Loading -> loadingState()
                        is HomeState.Success -> successState(state)
                        is HomeState.Error -> errorState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvTopics.visibility = View.GONE
    }

    private fun successState(state: HomeState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.rvTopics.visibility = View.VISIBLE
        topicAdapter.updateList(state.topics)
    }

    private fun errorState(state: HomeState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.rvTopics.visibility = View.GONE
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    private fun onItemSelected(topicId: Int) {
        val bundle = Bundle().apply {
            putInt("topic_id", topicId)
        }

        findNavController().navigate(
            R.id.conversationsFragment,
            bundle
        )
    }

}