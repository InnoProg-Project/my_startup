package com.innoprog.android.feature.feed.userprojectscreen.presentation

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentAnyProjectBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.feature.feed.anyProjectDetails.presentation.DocumentAdapter
import com.innoprog.android.feature.feed.userprojectscreen.di.DaggerUserProjectDetailsComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.uikit.R

class UserProjectDetailsFragment :
    BaseFragment<FragmentAnyProjectBinding, UserProjectViewModel>() {

    override val viewModel by injectViewModel<UserProjectViewModel>()
    val projectId: String? by lazy {
        arguments?.getString(PROJECT_ID)
    }

    override fun diComponent() = DaggerUserProjectDetailsComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    private val documentAdapter = DocumentAdapter { uri ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    }

    private val decorator: VerticalSpaceDecorator by lazy {
        VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_16))
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnyProjectBinding {
        return FragmentAnyProjectBinding.inflate(inflater, container, false)
    }

    companion object {
        const val PROJECT_ID = "project_id"
    }
}