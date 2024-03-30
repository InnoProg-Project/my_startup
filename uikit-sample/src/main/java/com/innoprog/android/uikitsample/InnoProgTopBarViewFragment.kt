package com.innoprog.android.uikitsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.innoprog.android.uikitsample.databinding.FragmentInnoProgTopBarViewBinding

class InnoProgTopBarViewFragment : Fragment() {
    private var _binding: FragmentInnoProgTopBarViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInnoProgTopBarViewBinding.inflate(inflater, container, false)
        return binding.root
    }
}
