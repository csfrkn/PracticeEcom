package com.fg.practiceecom.presentation.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fg.practiceecom.R
import com.fg.practiceecom.data.CartProduct
import com.fg.practiceecom.databinding.FragmentProductDetailsBinding
import com.fg.practiceecom.presentation.adapters.SizesAdapter
import com.fg.practiceecom.presentation.adapters.ViewPager2Images
import com.fg.practiceecom.presentation.viewmodel.DetailsViewModel
import com.fg.practiceecom.util.Resource
import com.fg.practiceecom.util.hideBottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
        setupViewpager()

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizesAdapter.onItemClick = {
            selectedSize = it
        }


        binding.buttonAddtoCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product!!, 1, selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.buttonAddtoCart.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.buttonAddtoCart.revertAnimation()
                        binding.buttonAddtoCart.setBackgroundColor(resources.getColor(R.color.black))
                    }

                    is Resource.Error -> {
                        binding.buttonAddtoCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product?.name
            tvProductPrice.text = "₺ ${product?.price}"
            tvProductDesc.text = product?.description

           // if (product?.sizes.isNullOrEmpty())
                //tvProductSizes.visibility = View.INVISIBLE

        }


        viewPagerAdapter.differ.submitList(product?.images)
        product?.sizes?.let { sizesAdapter.differ.submitList(it) }

    }

    private fun setupViewpager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

    private fun setupSizesRv() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}