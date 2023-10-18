package dev.passerby.cryptoxmlproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import dev.passerby.cryptoxmlproject.adapter.CoinsAdapter
import dev.passerby.cryptoxmlproject.adapter.FavoritesAdapter
import dev.passerby.cryptoxmlproject.databinding.FragmentHomeBinding
import dev.passerby.cryptoxmlproject.viewmodels.HomeViewModel
import dev.passerby.domain.models.CoinModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private var topCoinsList: List<CoinModel> = listOf()
    private var allCoinsList: List<CoinModel> = listOf()

    private lateinit var coinsAdapter: CoinsAdapter
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        initRecyclerView()
        initViewPager()
        observeViewModel()
        binding.homeShowAllButton.setOnClickListener {
            BottomSheetFragment().show(parentFragmentManager, "tag")
        }
    }

    private fun initAdapters() {
        coinsAdapter = CoinsAdapter(requireContext())
        favoritesAdapter = FavoritesAdapter(requireContext())
    }

    private fun initRecyclerView() {
        binding.homeMainRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = coinsAdapter
        }
    }

    private fun initViewPager() {
        binding.homeFavoritesPager.adapter = favoritesAdapter
        TabLayoutMediator(
            binding.homeFavoritesTabLayout,
            binding.homeFavoritesPager
        ) { _, _ -> }.attach()
    }

    private fun observeViewModel() {
        viewModel.topCoinsList.observe(viewLifecycleOwner) {
            topCoinsList = it
            favoritesAdapter.submitList(it)
            coinsAdapter.submitList(it)
        }
        viewModel.coinsList.observe(viewLifecycleOwner) {
            allCoinsList = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}