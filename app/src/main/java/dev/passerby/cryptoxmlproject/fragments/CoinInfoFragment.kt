package dev.passerby.cryptoxmlproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.databinding.FragmentCoinInfoBinding
import dev.passerby.cryptoxmlproject.factories.CoinInfoViewModelFactory
import dev.passerby.cryptoxmlproject.viewmodels.CoinInfoViewModel

class CoinInfoFragment : Fragment() {

    private var _binding: FragmentCoinInfoBinding? = null
    private val binding: FragmentCoinInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinInfoBinding is null")

    private val args by navArgs<CoinInfoFragmentArgs>()

    private val viewModelFactory by lazy {
        CoinInfoViewModelFactory(requireActivity().application, args.coinId)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coinInfo.observe(viewLifecycleOwner) { coin ->
            with(binding) {
                Glide.with(requireContext()).load(coin.icon).into(coinInfoLogoImageView)
                coinInfoNameTextView.text = coin.name
                coinInfoSymbolTextView.text = coin.symbol
                val bottomSheet = coinInfoMainViewStub.inflate()
                    bottomSheet.findViewById<TextView>(R.id.coinInfoPriceTextView).text = coin.price.toString()
                    bottomSheet.findViewById<TextView>(R.id.coinInfoChangeTextView).text =
                        coin.priceChange1h.toString()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}