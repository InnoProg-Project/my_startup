package com.innoprog.android.uikitsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.innoprog.android.uikit.InnoProgChipGroupView
import com.innoprog.android.uikitsample.databinding.FragmentInnoProgChipGroupBinding

class InnoProgChipGroupFragment : Fragment() {

    private var _binding: FragmentInnoProgChipGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInnoProgChipGroupBinding.inflate(inflater, container, false)
        val view = binding.root

        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS)
        binding.chipGroup.setChips(chipTitles)

        binding.chipGroup.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                // Если нужно обработать чип
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ALL_CONTENT = "Всё"
        private const val PROJECT = "Проекты"
        private const val IDEAS = "Идеи"
    }
}