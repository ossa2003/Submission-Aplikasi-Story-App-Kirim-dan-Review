package com.example.a01_storyapp.kumpulan_view.cerita

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityListCeritaBinding
import com.example.a01_storyapp.kumpulan_data.LoadingStateAdapter
import com.example.a01_storyapp.kumpulan_view.masuk.MasukActivity
import com.example.a01_storyapp.peta.PetaActivity

class ListCeritaActivity: AppCompatActivity() {
    private lateinit var binding: ActivityListCeritaBinding
    private val listViewmodel: ListCeritaViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var userPreferences: SharedPreferences
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.layoutManager = LinearLayoutManager(this)

        userPreferences = getSharedPreferences(MasukActivity.PREFS_NAME, Context.MODE_PRIVATE)
        name = userPreferences.getString(MasukActivity.NAME, "").toString()

        binding.salam.text = "Hello, $name"

        binding.photoAdd.setOnClickListener {
            val intent = Intent(this, TambahCeritaActivity::class.java)
            startActivity(intent)
        }

        getData()
    }

    private fun getData() {
        val token = userPreferences.getString(MasukActivity.TOKEN, "").toString()
        val adapter = CeritaAdapter()
        binding.rvList.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        listViewmodel.mendapatkanStory(token).observe(this,{
            adapter.submitData(lifecycle,it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutmenu -> {
                userPreferences.edit().apply {
                    clear()
                    apply()
                }
                val loginIntent = Intent(this, MasukActivity::class.java)
                startActivity(loginIntent)
                finish()
                return true
            }
            R.id.maps -> {
                val mapsIntent = Intent(this, PetaActivity::class.java)
                startActivity(mapsIntent)
                return true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AlertDialog.Builder(this).apply {
            setMessage(resources.getString(R.string.dialEx))

            setPositiveButton(resources.getString(R.string.respY)){dialog,which ->
                finishAffinity()
            }
            setNegativeButton(resources.getString(R.string.respT)){dialog,which ->
                dialog.cancel()
            }
            setCancelable(false)
        }.create().show()
    }
}