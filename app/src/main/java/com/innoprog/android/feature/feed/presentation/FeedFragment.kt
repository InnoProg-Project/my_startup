package com.innoprog.android.feature.feed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentFeedBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.di.DaggerFeedComponent
import com.innoprog.android.feature.feed.domain.models.Author
import com.innoprog.android.feature.feed.domain.models.Company
import com.innoprog.android.feature.feed.domain.models.News
import com.innoprog.android.uikit.InnoProgChipGroupView

class FeedFragment : BaseFragment<FragmentFeedBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<FeedViewModel>()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerFeedComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val company = Company(
            "HighTechCorp",
            "CEO"
        )

        val author = Author(
            "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "Юлия Анисимова",
            company
        )

        val news = News(
            id = "1",
            type = "idea",
            author = author,
            projectId = "123",
            title = "qwe",
            content = "asd",
            publishedAt = 24,
            likesCount = 24,
            commentsCount = 24,
        )

        binding.tvFeed.setOnClickListener {
            viewModel.onFavoriteClicked(news)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initChips()
    }

    private fun initButton() {
        binding.btnCreateIdea.setOnClickListener {
            Toast.makeText(requireContext(), "Переход на создание идеи", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChips() {
        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS)
        binding.cgvFilter.setChips(chipTitles)
        binding.cgvFilter.setOnChipSelectListener(object :
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
    }
}
