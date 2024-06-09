package com.oligue.app.biru.app.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.oligue.app.biru.R
import com.oligue.app.biru.utils.Utils
import com.oligue.app.biru.databinding.FragmentBrewerieDetailsBinding

class BrewerieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBrewerieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrewerieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brewerie = navArgs<BrewerieDetailsFragmentArgs>().value.BrewerieDetail

        binding.title.text = brewerie.name
        binding.descriptionTextView.text = resources.getString(
            R.string.Brewerie_description_info_text,
            brewerie.brewery_type,
            brewerie.phone
        )

        binding.descriptionLocation.text = resources.getString(
            R.string.Brewerie_location_info_text,
            brewerie.address_1,
            brewerie.city,
            brewerie.state,
            brewerie.country
        )

        binding.openMapsButton.setOnClickListener {
            openMaps(brewerie.latitude, brewerie.longitude)
        }

        binding.openSiteButton.setOnClickListener {
            openWebsite(brewerie.website_url)
        }
    }
    private fun openMaps(latitude: String?, longitude: String?){
        if (latitude == null || longitude == null){
            Utils.showSnackMessage(
                binding.root,
                resources.getString(R.string.Brewerie_not_found_text)
            )
            return
        }

        val gmmIntentUri = Uri.parse("google.streetview:cbll=${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        startActivity(mapIntent)
    }

    private fun openWebsite(url: String?) {
        if (url != null){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } else {
            Utils.showSnackMessage(
                binding.root,
                resources.getString(R.string.Brewerie_not_found_url_text)
            )
        }
    }
}