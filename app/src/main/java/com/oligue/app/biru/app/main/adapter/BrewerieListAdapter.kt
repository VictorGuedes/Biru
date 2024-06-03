package com.oligue.app.biru.app.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.databinding.BrewerieItemListBinding

class BrewerieListAdapter(private val mapsAdapter: MapsAdapter) :
    PagingDataAdapter<Brewerie, BrewerieListAdapter.BrewerieViewHolder>(BrewerieDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrewerieViewHolder {
        return BrewerieViewHolder(
            BrewerieItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: BrewerieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, position)
    }


    inner class BrewerieViewHolder(
        private val binding: BrewerieItemListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Brewerie?, position: Int){
            binding.let {
                binding.city.text = data?.city
                if (position == 0) updateMap(data)

                it.root.setOnClickListener{
                    updateMap(data)
                }
            }
        }

        private fun updateMap(data: Brewerie?){
            mapsAdapter.onUpdateMaps(data)
        }
    }

    private class BrewerieDiffCallback : DiffUtil.ItemCallback<Brewerie>() {
        override fun areItemsTheSame(oldItem: Brewerie, newItem: Brewerie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Brewerie, newItem: Brewerie): Boolean {
            return oldItem == newItem
        }
    }
}