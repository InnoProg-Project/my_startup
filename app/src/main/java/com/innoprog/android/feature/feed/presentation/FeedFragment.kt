package com.innoprog.android.feature.feed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentFeedBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.di.DaggerFeedComponent
import com.innoprog.android.feature.feed.domain.models.News

class FeedFragment : BaseFragment<FragmentFeedBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<FeedViewModel>()
    // override fun diComponent(): ScreenComponent = DaggerFeedComponent.builder().build()

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

        val news = News(
            id = "1",
            type = "idea",
            author = "he",
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
}
