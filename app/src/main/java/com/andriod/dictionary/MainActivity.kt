package com.andriod.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andriod.dictionary.databinding.ActivityMainBinding
import com.andriod.dictionary.utils.app
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val navigator by lazy { AppNavigator(this, binding.container.id) }

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app.appComponent.inject(this)

        if (savedInstanceState == null) {
            router.replaceScreen(Screens.WordList())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        app.appComponent.getNavigationHolder().setNavigator(navigator)
    }

    override fun onPause() {
        app.appComponent.getNavigationHolder().removeNavigator()
        super.onPause()
    }
}