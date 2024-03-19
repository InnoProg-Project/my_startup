package com.innoprog.android.uikitsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.uikitsample.databinding.FragmentInnoProgButtonViewBinding

class InnoProgButtonViewFragment : Fragment() {

    private var _binding: FragmentInnoProgButtonViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInnoProgButtonViewBinding.inflate(inflater,container,false)
        return binding.root
    }
}
