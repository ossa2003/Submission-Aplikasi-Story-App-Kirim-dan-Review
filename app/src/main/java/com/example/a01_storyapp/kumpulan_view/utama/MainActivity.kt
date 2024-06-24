package com.example.a01_storyapp.kumpulan_view.utama

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityMainBinding
import com.example.a01_storyapp.kumpulan_response.ListStoryItem
import com.example.a01_storyapp.kumpulan_view.cerita.CeritaAdapter
import com.example.a01_storyapp.kumpulan_view.cerita.DetilStoryActivity
import com.example.a01_storyapp.kumpulan_view.cerita.TambahCeritaActivity
import com.example.a01_storyapp.kumpulan_view.landing.WelcomeActivity
import com.example.a01_storyapp.kumpulan_view.masuk.MasukActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ViewModelMain> { FactoryViewModel.mendapatkanInstance(this) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: CeritaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mengaturView()
        observasiSesi()
        observasiKeluar()
        gabungSemuaStory()
        observasiListStory()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahCeritaActivity::class.java))
        }
    }

    private fun observasiSesi() {
        viewModel.mendapatkanSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun observasiKeluar(){
        binding.bottomNavigationView.setOnItemSelectedListener {  menuItem ->
            when (menuItem.itemId){
                R.id.logoutmenu -> {
                    binding.bottomAppBar.setOnMenuItemClickListener(null)

                    AlertDialog.Builder(this).apply {
                        setTitle("Confirmation to Logout")
                        setMessage("Are you sure want to Logout?")
                        setPositiveButton("Yes") {dialog, _ ->
                            dialog.dismiss()
                            viewModel.keluar()
                            binding.bottomAppBar.setOnMenuItemClickListener{innerMenu ->
                                observasiKeluar()
                                true
                            }
                            val intent = Intent(this@MainActivity, MasukActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        setNegativeButton("No") {dialog, _ ->
                            dialog.dismiss()
                            binding.bottomAppBar.setOnMenuItemClickListener{innerMenu ->
                                observasiKeluar()
                                true
                            }
                        }
                        create()
                        show()
                    }
                    true
                }
                R.id.action_home -> {
                    recreate()
                    true
                }
                else -> false
            }
        }
    }

    private fun gabungSemuaStory() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                viewModel.mendapatkanSemuaStory()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}")
            }
        }
    }

    private fun observasiListStory() {
        viewModel.listStory.observe(this) { storyList ->
            if (storyList.isNotEmpty()) {
                mengaturStoryAdapter(storyList)
            } else {
                menunjukkanEmptyStoryToast()
                mengaturEmptyStoryAdapter()
            }
        }
    }

    private fun mengaturStoryAdapter(storyList: List<ListStoryItem>) {
        storyAdapter = CeritaAdapter(storyList, object : CeritaAdapter.OnAdapterListener {
            override fun onClick(story: ListStoryItem) {
                navigateToDetailStory(story)
            }
        })
        binding.rvStory.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun mengaturEmptyStoryAdapter() {
        storyAdapter = CeritaAdapter(mutableListOf(), object : CeritaAdapter.OnAdapterListener {
            override fun onClick(story: ListStoryItem) {
                // Handle empty state
            }
        })
        binding.rvStory.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun menunjukkanEmptyStoryToast() {
        Toast.makeText(this, ("Emppty Story"), Toast.LENGTH_SHORT).show()
    }

    private fun mengaturView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun navigateToDetailStory(story: ListStoryItem) {
        Intent(this, DetilStoryActivity::class.java).apply {
            putExtra(DetilStoryActivity.EXTRA_PHOTO_URL, story.photoUrl)
            putExtra(DetilStoryActivity.EXTRA_CREATED_AT, story.createdAt)
            putExtra(DetilStoryActivity.EXTRA_NAME, story.name)
            putExtra(DetilStoryActivity.EXTRA_DESCRIPTION, story.description)
            putExtra(DetilStoryActivity.EXTRA_LON, story.lon)
            putExtra(DetilStoryActivity.EXTRA_ID, story.id)
            putExtra(DetilStoryActivity.EXTRA_LAT, story.lat)
        }.also {
            startActivity(it)
        }
    }
}
