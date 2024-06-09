package com.oligue.app.biru.app.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oligue.app.biru.app.main.model.BrewerieUI
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.databinding.BrewerieItemListBinding

class BrewerieListAdapter(private val mapsAdapter: MapsAdapter) :
    PagingDataAdapter<BrewerieUI, BrewerieListAdapter.BrewerieViewHolder>(BrewerieDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrewerieViewHolder {
        return BrewerieViewHolder(
            BrewerieItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: BrewerieViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data, position)
        }
    }


    inner class BrewerieViewHolder(
        private val binding: BrewerieItemListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BrewerieUI, position: Int){
            binding.let {
                binding.brewerieName.text = data.name
                binding.brewerieCityState.text = "${data.city} - ${data.state}"
                if (position == 0) updateMap(data)

                it.root.setOnClickListener{
                    updateMap(data)
                }
            }
        }

        private fun updateMap(data: BrewerieUI){
            mapsAdapter.onUpdateMaps(data)
        }
    }

    private class BrewerieDiffCallback : DiffUtil.ItemCallback<BrewerieUI>() {
        override fun areItemsTheSame(oldItem: BrewerieUI, newItem: BrewerieUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BrewerieUI, newItem: BrewerieUI): Boolean {
            return oldItem == newItem
        }
    }
}