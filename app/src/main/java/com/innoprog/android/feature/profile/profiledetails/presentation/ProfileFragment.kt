package com.innoprog.android.feature.profile.profiledetails.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.feature.profile.profiledetails.di.DaggerProfileComponent
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWithProject
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.feature.profile.profiledetails.presentation.adapter.PublicationsRecyclerAdapter

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<ProfileViewModel>()
    private val publicationsAdapter: PublicationsRecyclerAdapter by lazy {
        PublicationsRecyclerAdapter(publications) { publication ->
            when (publication.feedWrapper) {
                is FeedWrapper.Idea -> {
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToIdeaDetailsFragment(
                            publication.feedWrapper.id
                        )
                    findNavController().navigate(action)
                }

                is FeedWrapper.News -> {
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToNewsDetailsFragment(
                            publication.feedWrapper.id
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private var user: Profile? = null
    private var userCompany: ProfileCompany? = null
    private var publications: ArrayList<FeedWithProject> = arrayListOf()

    override fun diComponent(): ScreenComponent {
        return DaggerProfileComponent
            .builder()
            .appComponent(AppComponentHolder.getComponent())
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
        observeData()
        viewModel.loadProfile()
        viewModel.loadProfileCompany()
        initTopBar()
        initChips()
        initAdapters()
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfileScreenState.Content -> {
                    user = state.profileInfo
                    render(state)
                    viewModel.loadChipAll(authorId = state.profileInfo.userId)
                }

                is ProfileScreenState.Error -> showError()
            }

            viewModel.uiStateCompany.observe(viewLifecycleOwner) { stateCompany ->
                renderCompany(stateCompany)
            }
            viewModel.chipsUiState.observe(viewLifecycleOwner) { stateChips ->
                renderChips(stateChips)
            }
        }
    }

    private fun initChips() {
        val chipTitles = resources.getStringArray(R.array.profile_chips).toList()
        binding.chips.setChips(chipTitles)
        binding.chips.selectChip(INDEX_1)
        binding.chips.setOnChipSelectListener { chipIndex ->
            user?.let { user ->
                when (chipIndex) {
                    INDEX_1 -> viewModel.loadChipAll(authorId = user.userId)
                    INDEX_2 -> viewModel.loadChipProjects(
                        type = PublicationType.NEWS.value,
                        authorId = user.userId
                    )

                    INDEX_3 -> viewModel.loadChipIdeas(
                        type = PublicationType.IDEA.value,
                        authorId = user.userId
                    )

                    INDEX_4 -> viewModel.loadChipLiked(pageSize = PAGE_SIZE)
                    INDEX_5 -> viewModel.loadChipFavorites(pageSize = PAGE_SIZE)
                }
            }
        }
    }

    private fun initTopBar() {
        binding.topbarProfile.setRightIconClickListener {
            user?.let { user ->
                userCompany?.let { company ->
                    val direction = ProfileFragmentDirections.actionProfileFragmentToProfileBottomSheet(user, company)
                    findNavController().navigate(direction)
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            recyclerContent.adapter = publicationsAdapter
        }
    }

    private fun render(screenState: ProfileScreenState) {
        when (screenState) {
            is ProfileScreenState.Content -> fillViews(screenState.profileInfo)

            is ProfileScreenState.Error -> showError()
        }
    }

    private fun renderCompany(screenStateCompany: ProfileCompanyScreenState) {
        when (screenStateCompany) {
            is ProfileCompanyScreenState.Content -> {
                fillViewsCompany(screenStateCompany.profileCompany)
            }

            is ProfileCompanyScreenState.Error -> showError()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderChips(chipsScreenState: ChipsScreenState) {
        if (chipsScreenState !is ChipsScreenState.Error) {
            publications.clear()
        }
        when (chipsScreenState) {
            is ChipsScreenState.All -> publications.addAll(chipsScreenState.content)

            is ChipsScreenState.Projects -> publications.addAll(chipsScreenState.projects)

            is ChipsScreenState.Ideas -> publications.addAll(chipsScreenState.ideas)

            is ChipsScreenState.Liked -> publications.addAll(chipsScreenState.liked)

            is ChipsScreenState.Favorites -> publications.addAll(chipsScreenState.favorites)

            is ChipsScreenState.Error -> showPlaceholder()
        }
        if (chipsScreenState !is ChipsScreenState.Error) {
            publicationsAdapter.notifyDataSetChanged()
            showContent()
        }
    }

    private fun fillViews(profile: Profile) {
        binding.name.text = profile.name
        binding.description.text = profile.about
        val initials = profile.name
            .split(' ')
            .map { it.first().uppercaseChar() }
            .joinToString(separator = "", limit = 2)
        binding.avatar.text = initials.ifBlank { "?" }
    }

    private fun fillViewsCompany(company: ProfileCompany) {
        binding.position.text = company.role
        binding.companyName.text = company.name
        binding.profileIn.isVisible = true
    }

    private fun showError() {
        with(binding) {
            name.isVisible = false
            description.isVisible = false
            position.isVisible = false
            profileIn.isVisible = false
            companyName.isVisible = false
        }
    }

    private fun showPlaceholder() {
        if (publicationsAdapter.publications.isEmpty()) {
            with(binding) {
                recyclerContent.isVisible = false
                placeholderText.isVisible = true
            }
        }
    }

    private fun showContent() {
        with(binding) {
            if (publicationsAdapter.publications.isEmpty()) {
                placeholderText.isVisible = true
                recyclerContent.isVisible = false
            } else {
                placeholderText.isVisible = false
                recyclerContent.isVisible = true
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val INDEX_1 = 0
        private const val INDEX_2 = 1
        private const val INDEX_3 = 2
        private const val INDEX_4 = 3
        private const val INDEX_5 = 4
    }
}
