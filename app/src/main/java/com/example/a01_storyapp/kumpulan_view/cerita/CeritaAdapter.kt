package com.example.a01_storyapp.kumpulan_view.cerita

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a01_storyapp.databinding.ItemListCeritaBinding
import com.example.a01_storyapp.kumpulan_response.ListStoryItem

class CeritaAdapter
    : PagingDataAdapter<ListStoryItem, CeritaAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemListCeritaBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)as ListStoryItem)
    }

    class ViewHolder(private val binding: ItemListCeritaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem: ListStoryItem){
            binding.apply {
                Glide.with(itemView)
                    .load(listStoryItem.photoUrl)
                    .into(binding.imgPhoto)

                binding.tvName.text = listStoryItem.name
            }
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetilStoryActivity::class.java)
                intent.putExtra(DETAIL_STORIES, listStoryItem)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
        const val DETAIL_STORIES = "detail_stories"
    }
}