package com.martialcoder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.martialcoder.adapter.AdapterTruckslist
import com.martialcoder.api.TrucksDataService
import com.martialcoder.model.Data
import com.martialcoder.remotestate.R
import com.martialcoder.remotestate.databinding.ActivityMainBinding
import com.martialcoder.repository.Repository
import com.martialcoder.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: AdapterTruckslist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val repo = Repository(TrucksDataService)
        viewModel = ViewModelProvider(this, ViewModelFactory(repo)).get(MainViewModel::class.java)
        initAdapter()
        fetchData()

        binding.mapImg.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        }
    }

    private fun initAdapter() {
        adapter = AdapterTruckslist(arrayListOf())
        binding.trucksRecycler.adapter = adapter
    }

    private fun fetchData() {
        viewModel.getTRucksData(
            "PCH",
            33,
            false,
            "g2qb5jvucg7j8skpu5q7ria0mu",
            true,
            "lastRunningState,lastWaypoint"
        ).observe(this, Observer {
            it?.let { stateData ->
                when (stateData.status) {
                    StateData.DataStatus.SUCCESS -> {
                        val data = stateData.data?.data as ArrayList<Data>
                        adapter.initList(data)
                    }
                    StateData.DataStatus.ERROR -> {
                        Toast.makeText(this, stateData.error?.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    StateData.DataStatus.LOADING -> {

                    }
                }
            }
        })
    }
}