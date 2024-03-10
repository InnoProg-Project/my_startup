package com.innoprog.android.uikitsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.innoprog.android.uikitsample.databinding.InnoProgAvatarViewLayoutBinding

class InnoProgAvatarViewFragment : Fragment() {

    private var _binding: InnoProgAvatarViewLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InnoProgAvatarViewLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.getDrawable(R.drawable.avatar_test_img)
            ?.let { binding.avatar3.setEditableAvatar(it) }
        binding.avatar4.setEditablePlaceholder()
    }
}