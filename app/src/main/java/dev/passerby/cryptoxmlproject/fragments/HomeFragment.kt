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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

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

        coinsAdapter = CoinsAdapter(requireContext())
        favoritesAdapter = FavoritesAdapter(requireContext())
        binding.homeMainRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = coinsAdapter
        }
        binding.homeFavoritesPager.adapter = favoritesAdapter
        TabLayoutMediator(binding.homeFavoritesTabLayout, binding.homeFavoritesPager) { _, _ ->

        }.attach()
        viewModel.topCoinsList.observe(viewLifecycleOwner) {
            coinsAdapter.submitList(it)
            favoritesAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}