package com.innoprog.android.feature.training.videoPlayer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentVideoPlayerBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.courseInformation.presentation.CourseInformationFragment.Companion.VIDEO_PLAYER_KEY
import com.innoprog.android.feature.training.videoPlayer.di.DaggerVideoPlayerComponent

private var playbackPosition = 0L

class VideoPlayerFragment : BaseFragment<FragmentVideoPlayerBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<VideoPlayerViewModel>()
    override fun diComponent(): ScreenComponent = DaggerVideoPlayerComponent.builder().build()
    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVideoPlayerBinding {
        return FragmentVideoPlayerBinding.inflate(inflater, container, false)
    }

    private val url by lazy { arguments?.getString(VIDEO_PLAYER_KEY) }
    private val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        binding.playerView.player = exoPlayer
        exoPlayer.setMediaItem(MediaItem.fromUri(url!!), playbackPosition)
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

    private fun releasePlayer() {
        playbackPosition = exoPlayer.currentPosition
        exoPlayer.release()
    }
}
