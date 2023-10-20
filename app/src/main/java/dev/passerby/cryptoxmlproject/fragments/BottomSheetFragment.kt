package dev.passerby.cryptoxmlproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.adapter.CoinsAdapter
import dev.passerby.cryptoxmlproject.databinding.FragmentBottomSheetBinding
import dev.passerby.cryptoxmlproject.viewmodels.HomeViewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    // Можно обойтись без биндинга и использовать findViewById
    lateinit var binding: FragmentBottomSheetBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private lateinit var coinsAdapter: CoinsAdapter

    // Переопределим тему, чтобы использовать нашу с закруглёнными углами

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentBottomSheetBinding.bind(
                inflater.inflate(
                    R.layout.fragment_bottom_sheet,
                    container
                )
            )
        return binding.root
    }

    // Я выбрал этот метод ЖЦ, и считаю, что это удачное место
    // Вы можете попробовать производить эти действия не в этом методе ЖЦ, а например в onCreateDialog()
    override fun onStart() {
        super.onStart()

        coinsAdapter = CoinsAdapter(requireContext())

        binding.homeBigRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = coinsAdapter
        }

        viewModel.coinsList.observe(viewLifecycleOwner) {
            coinsAdapter.submitList(it)
        }

        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // Плотность понадобится нам в дальнейшем
        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            // Находим сам bottomSheet и достаём из него Behaviour
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
            val collapsedHeight = resources.displayMetrics.heightPixels / 2
            behavior.peekHeight = (collapsedHeight * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior.maxHeight = resources.displayMetrics.heightPixels - 64

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }
}