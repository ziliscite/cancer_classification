package com.dicoding.asclepius.view.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.helper.LocalViewModelFactory

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels<HistoryViewModel> {
        LocalViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getHistories().observe(viewLifecycleOwner) {
            setHistories(it)
        }
    }

    private fun setHistories(results: List<ClassificationResult>) {
        val adapter = HistoryAdapter {
            // Result is from history, so isSaved = true, also have id
            val toResult =  HistoryFragmentDirections.actionNavigationHistoryToResultActivity(it, true)
            findNavController().navigate(toResult)
        }
        adapter.submitList(results)
        binding.rvHistory.adapter = adapter
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHistory.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}