package com.example.firstmultiplatformproject.android.presentation.ui.main_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.firstmultiplatformproject.android.R
import com.example.firstmultiplatformproject.android.presentation.ui.hilt_compose_recipe.HiltComposeRecipeActivity
import com.example.firstmultiplatformproject.android.presentation.ui.login.LoginActivity
import com.example.firstmultiplatformproject.shared.Greeting
import com.example.firstmultiplatformproject.shared.datasource.network.SpaceXSDK
import com.example.firstmultiplatformproject.shared.datasource.cache.DriverFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    private val mainScope = MainScope()

    private lateinit var scrollToTop: FloatingActionButton
    private lateinit var btnLogin: Button
    private lateinit var btnHiltCompose: Button
    private lateinit var launchesRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val sdk = SpaceXSDK(DriverFactory(this))

    private val launchesRvAdapter = LaunchesRvAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SpaceX Launches"
        setContentView(R.layout.activity_main)
    }

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onStart() {
        super.onStart()

        initializeUi()
    }

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    private fun initializeUi() {

        btnLogin = findViewById(R.id.btnLogin)
        btnHiltCompose = findViewById(R.id.btnHiltCompose)
        launchesRecyclerView = findViewById(R.id.launchesListRv)
        progressBarView = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)
        scrollToTop = findViewById(R.id.scrollToTop)

        launchesRecyclerView.adapter = launchesRvAdapter
        launchesRecyclerView.layoutManager = LinearLayoutManager(this)

        initalizeScreenEventsListeners()

        displayLaunches(false)
    }

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    private fun initalizeScreenEventsListeners() {

        btnLogin.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        btnHiltCompose.setOnClickListener {
            val loginIntent = Intent(this, HiltComposeRecipeActivity::class.java)
            startActivity(loginIntent)
        }

        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(this@MainActivity) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        scrollToTop.setOnClickListener {
            smoothScroller.setTargetPosition(0)
            (launchesRecyclerView.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayLaunches(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun displayLaunches(needReload: Boolean) {
        progressBarView.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                sdk.getLaunches(needReload)
            }.onSuccess {
                launchesRvAdapter.launches = it
                launchesRvAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false
        }
    }

}
