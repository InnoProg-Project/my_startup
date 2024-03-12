package com.innoprog.android.uikitsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.innoprog.android.uikitsample.databinding.FragmentInnoProgLikeViewBinding

class InnoProgLikeViewFragment : Fragment() {

    private var _binding: FragmentInnoProgLikeViewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInnoProgLikeViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.likeButton1.setOnClickListener {
            binding.likeButton1.click()
        }
        binding.likeButton2.setOnClickListener {
            binding.likeButton2.click()
        }
    }
}
