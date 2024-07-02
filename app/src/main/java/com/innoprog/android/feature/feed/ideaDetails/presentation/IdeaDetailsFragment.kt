package com.innoprog.android.feature.feed.ideaDetails.presentation

import androidx.core.view.isVisible
import com.innoprog.android.R
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.feed.newsdetails.presentation.NewsDetailsFragment

class IdeaDetailsFragment : NewsDetailsFragment() {

    override fun showContent(newsDetails: NewsDetailsModel) {
        super.showContent(newsDetails)
        binding.apply {
            newsTopBar.setTitleText(getString(R.string.idea))
            tvAboutProjectTitle.isVisible = false
            projectCard.isVisible = false
        }
    }
}
