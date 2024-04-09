package com.innoprog.android.feature.feed.newsfeed.presentation

import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val favoritesInteractor: FavoritesInteractor) :
    BaseViewModel() {
    fun onFavoriteClicked(news: News) {
        viewModelScope.launch {
            favoritesInteractor.insertNewsToFavorites(news)
        }
    }
}
