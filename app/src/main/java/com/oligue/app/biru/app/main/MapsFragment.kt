package com.oligue.app.biru.app.main

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.oligue.app.biru.R
import com.oligue.app.biru.utils.Utils
import com.oligue.app.biru.app.main.adapter.BrewerieListAdapter
import com.oligue.app.biru.app.main.adapter.MapsAdapter
import com.oligue.app.biru.app.main.viewmodel.MainViewModel
import com.oligue.app.biru.app.main.viewmodel.ListBreweriesUiState
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.databinding.FragmentMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsFragment : Fragment(), MapsAdapter, GoogleMap.OnMarkerClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: BrewerieListAdapter
    private lateinit var binding: FragmentMapsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupAdapter()
        setupViewModel()
    }

    private fun setupUI(){
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 300
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.inputSearchEditText.setOnEditorActionListener { textView, actionId, event ->
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                viewModel.getBreweriesBySearch(textView.text.toString())
            }
            false
        }
    }

    private fun setupAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(binding.recyclerView.context)
            setHasFixedSize(true)
        }
        adapter = BrewerieListAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getBreweries().observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner){ state ->
            when(state){
                is ListBreweriesUiState.Loading -> {
                }
                is ListBreweriesUiState.Success -> {
                    adapter.submitData(lifecycle, PagingData.from(state.model))
                }
                is ListBreweriesUiState.Error -> {
                    Utils.showSnackMessage(binding.root, state.errorMessage)
                }
            }
        }
    }

    override fun onUpdateMaps(data: Brewerie?) {
        val latitude = data?.latitude?.toDouble() ?: 0.0
        val longitude = data?.longitude?.toDouble() ?: 0.0

        if (latitude != 0.0 || longitude != 0.0){
            val callback = OnMapReadyCallback { googleMap ->
                val city = LatLng(latitude, longitude)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(city)
                        .title(data?.name)
                )
                viewModel.putBrewerieHashMap(city, data)

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(city))
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(5f), 2000, null)
                googleMap.setOnMarkerClickListener(this)
            }

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)

            return
        }

        Utils.showSnackMessage(
            binding.root,
            resources.getString(R.string.Brewerie_not_found_text),
        )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val brewerie = viewModel.getBrewerieDataFromHashMap(marker.position)

        if (brewerie != null) {
            findNavController().navigate(
                MapsFragmentDirections.
                actionMapsFragmentToBrewerieDetailsFragment(
                    brewerie
                )
            )
        }

        return false
    }
}