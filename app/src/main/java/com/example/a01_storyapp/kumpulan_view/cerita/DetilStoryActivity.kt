package com.example.a01_storyapp.kumpulan_view.cerita

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityDetilStoryBinding
import com.example.a01_storyapp.kumpulan_response.ListStoryItem

class DetilStoryActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetilStoryBinding
    private lateinit var detailStories: ListStoryItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetilStoryBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_back)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        detailStories = intent.getParcelableExtra<ListStoryItem>(CeritaAdapter.DETAIL_STORIES) as ListStoryItem
        supportActionBar?.title = detailStories.name

        setData()
    }

    private fun setData() {
        detailBinding.apply {
            Glide.with(this@DetilStoryActivity)
                .load(detailStories.photoUrl)
                .into(detailBinding.profileImageView)
            nameTextView.text = detailStories.name
            descTextView.text = detailStories.description
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }
}