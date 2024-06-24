package com.example.a01_storyapp

import com.example.a01_storyapp.kumpulan_response.ListStoryItem


object DummyData {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "photo url $i",
                "create at $i",
                "name $i",
                "description $i",
                1.0,
                "id + $i",
                1.0,
            )
            items.add(story)
        }
        return items
    }
}