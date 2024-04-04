package com.innoprog.android.uikitsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.innoprog.android.uikit.InnoProgInputViewState
import com.innoprog.android.uikitsample.databinding.FragmentInnoProgSmsCodeViewBinding

class InnoProgSMSCodeViewFragment : Fragment() {

    private var _binding: FragmentInnoProgSmsCodeViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInnoProgSmsCodeViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.codeBox1.setState(InnoProgInputViewState.INACTIVE)
        binding.codeBox2.setState(InnoProgInputViewState.DISABLED)
        binding.codeBox3.setState(InnoProgInputViewState.ERROR)
        binding.codeBox4.setState(InnoProgInputViewState.FOCUSED)
    }
}
