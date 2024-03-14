package com.innoprog.android.uikitsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.uikitsample.databinding.FragmentInnoProgChipBinding

class InnoProgChipFragment : Fragment() {

    private var _binding: FragmentInnoProgChipBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInnoProgChipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipView1.setOnClickListener {
            binding.chipView1.checkedChip()
        }
        binding.chipView2.setOnClickListener {
            binding.chipView2.checkedChip()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}