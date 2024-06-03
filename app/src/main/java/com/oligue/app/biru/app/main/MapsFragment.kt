package com.oligue.app.biru.app.main

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

import com.oligue.app.biru.R
import com.oligue.app.biru.app.main.adapter.BrewerieListAdapter
import com.oligue.app.biru.app.main.adapter.MapsAdapter
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.databinding.FragmentMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsFragment : Fragment(), MapsAdapter {

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
        setupAdapter()
        setupViewModel()
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
                viewModel.putBrewerieHashMap(city,data)

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(city))
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(5f), 2000, null)


                googleMap.setOnMarkerClickListener { marker ->
                    val brewerie = viewModel.getBrewerieDataFromHashMap(marker.position)
                    if (brewerie != null) {
                        findNavController().navigate(
                            MapsFragmentDirections.
                            actionMapsFragmentToBrewerieDetailsFragment(
                                brewerie
                            )
                        )
                    }
                    false
                }

            }

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)

            return
        }

        Snackbar.make(
            binding.root,
            resources.getString(R.string.Brewerie_not_found_text),
            Snackbar.LENGTH_LONG
        ).show()
    }
}