package com.fg.practiceecom.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fg.practiceecom.R
import com.fg.practiceecom.databinding.ActivityShoppingBinding
import com.fg.practiceecom.presentation.fragments.shopping.CartFragment
import com.fg.practiceecom.presentation.fragments.shopping.CartFragmentDirections
import com.fg.practiceecom.presentation.viewmodel.CartViewModel
import com.fg.practiceecom.util.Resource
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }

    val viewModel by viewModels<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //----------------inflate class hatası çözümü
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.shoppingHostFragment) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        lifecycleScope.launchWhenStarted {
            x()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private suspend fun x() {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.cartProducts.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val count = it.data?.size ?: 0
                        binding.bottomNavigation.getOrCreateBadge(R.id.cartFragment).apply {
                            number = count
                            backgroundColor = resources.getColor(R.color.gray)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}