package com.example.a01_storyapp.kumpulan_view.cerita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a01_storyapp.R
import com.example.a01_storyapp.kumpulan_response.ListStoryItem

// Mendeklarasikan kelas CeritaAdapter yang menerima dua parameter: ceritaList dan listener
class CeritaAdapter(
    // Daftar cerita yang akan ditampilkan dalam RecyclerView
    private val ceritaList: List<ListStoryItem>,
    // Listener untuk menangani klik pada item
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<CeritaAdapter.StoryViewHolder>() {

    // Mendeklarasikan interface OnAdapterListener dengan fungsi onClick
    interface OnAdapterListener {
        fun onClick(story: ListStoryItem)
    }

    // Membuat ViewHolder baru untuk setiap item dalam RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        // Menginflasi layout item_row_hero untuk item dalam RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        // Mengembalikan instance StoryViewHolder
        return StoryViewHolder(view)
    }

    // Mengikat data ke ViewHolder untuk setiap item
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        // Mendapatkan data cerita dari posisi saat ini
        val story = ceritaList[position]
        // Mengikat data cerita ke ViewHolder
        holder.bind(story)
        // Menetapkan onClickListener untuk item
        holder.itemView.setOnClickListener {
            listener.onClick(story)
        }
    }

    // Mengembalikan jumlah item dalam RecyclerView
    override fun getItemCount(): Int {
        return ceritaList.size
    }

    // Mendeklarasikan kelas ViewHolder untuk menyimpan referensi ke view item
    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Mengambil referensi TextView untuk nama cerita
        val name: TextView = itemView.findViewById(R.id.nameTextView)
        // Mengambil referensi ImageView untuk foto cerita
        val photo: ImageView = itemView.findViewById(R.id.profileImageView)

        // Mengikat data cerita ke view item
        fun bind(story: ListStoryItem) {
            // Menetapkan teks nama cerita ke TextView
            name.text = story.name
            // Menggunakan Glide untuk memuat foto cerita ke ImageView
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .error(R.drawable.ic_launcher_background) // Gambar yang ditampilkan jika terjadi kesalahan
                .into(photo)
        }
    }
}
