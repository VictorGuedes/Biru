package com.oligue.app.biru.app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.oligue.app.biru.app.main.adapter.BrewerieListAdapter
import com.oligue.app.biru.app.main.viewmodel.MainViewModel
import com.oligue.app.biru.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: BrewerieListAdapter
    private lateinit var binding: MainFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViewModel()
    }

    private fun setupAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(binding.recyclerView.context)
            setHasFixedSize(true)
        }
        //adapter = BrewerieListAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getBreweries().observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }
}