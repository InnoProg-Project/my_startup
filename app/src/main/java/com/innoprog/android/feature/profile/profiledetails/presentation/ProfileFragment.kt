package com.innoprog.android.feature.profile.profiledetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.feature.profile.di.DaggerProfileComponent
import com.innoprog.android.uikit.InnoProgChipGroupView

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ProfileViewModel>()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerProfileComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMoreButton()

        initChips()
    }

    private fun initMoreButton() {
        binding.buttonMore.setOnClickListener { button ->
            (button as? ImageView)?.let { startAnimation(it) }
            findNavController().navigate(R.id.action_profileFragment_to_profile_bottom_sheet)
        }
    }

    private fun startAnimation(button: ImageView) {
        button.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.scale
            )
        )
    }

    private fun initChips() {
        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS, LIKES, FAVORITES)
        binding.chips.setChips(chipTitles)
        binding.chips.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                // Если нужно обработать чип
            }
        })
    }

    companion object {

        private const val ALL_CONTENT = "Всё"
        private const val PROJECT = "Проекты"
        private const val IDEAS = "Идеи"
        private const val LIKES = "Лайкнутые"
        private const val FAVORITES = "Избранное"
    }
}
