package dev.passerby.cryptoxmlproject.fragments

import android.graphics.ColorFilter
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
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
                coinInfoMainViewStub.visibility = View.VISIBLE
                val behavior = BottomSheetBehavior.from(coinInfoBottomSheetContainer)
                behavior.peekHeight = (resources.displayMetrics.heightPixels * 0.45).toInt()
                behavior.maxHeight = (resources.displayMetrics.heightPixels * 0.9).toInt()
                behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        if (slideOffset > 0.5f) {
                            coinInfoMainViewStub.visibility = View.GONE
                            coinInfoExpandedViewStub.visibility = View.VISIBLE
                        } else {
                            coinInfoMainViewStub.visibility = View.VISIBLE
                            coinInfoExpandedViewStub.visibility = View.GONE
                        }
                    }
                })

                coinInfoFavButton.setOnClickListener {
                    behavior.state = if (behavior.state == STATE_COLLAPSED) {
                        STATE_EXPANDED
                    } else {
                        STATE_COLLAPSED
                    }
                }
                coinInfoBackButton.setOnClickListener {
                    setBW(coinInfoLogoImageView)
                }
            }
        }
    }

    private fun setBW(iv: ImageView) {
        val brightness = 10f // change values to suite your need
        val colorMatrix = floatArrayOf(
            0.33f, 0.33f, 0.33f, 0f, brightness,
            0.33f, 0.33f, 0.33f, 0f, brightness,
            0.33f, 0.33f, 0.33f, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
        )
        val colorFilter: ColorFilter = ColorMatrixColorFilter(colorMatrix)
        iv.colorFilter = colorFilter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}