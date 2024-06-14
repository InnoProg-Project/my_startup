package com.innoprog.android.feature.profile.profiledetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.newsfeed.presentation.FeedFragmentDirections
import com.innoprog.android.feature.profile.profiledetails.di.DaggerProfileComponent
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.uikit.InnoProgChipGroupView

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ProfileViewModel>()

    private var user: Profile? = null

    private var publicationList: ArrayList<FeedWrapper> = arrayListOf()

    private val adapter: RecyclerAdapter by lazy {
        RecyclerAdapter(publicationList) { publication ->
            when (publication) {
                is FeedWrapper.Idea -> {
                    val action = FeedFragmentDirections.actionFeedFragmentToNewsDetailsFragment(publication.id)
                    findNavController().navigate(action)
                }
                is FeedWrapper.News -> {
                    val action = FeedFragmentDirections.actionFeedFragmentToNewsDetailsFragment(publication.id)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerProfileComponent
            .builder()
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

        observeData()

        viewModel.loadProfile()

        viewModel.loadProfileCompany()

        initTopBar()

        initChips()

        initAdapter()
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfileScreenState.Content -> {
                    user = state.profileInfo
                    render(state)
                    user?.let { user ->
                        viewModel.loadChipAll(authorId = user.userId)
                    }
                }

                is ProfileScreenState.Error -> {
                    showError()
                }
            }

            viewModel.uiStateCompany.observe(viewLifecycleOwner) { state ->
                renderCompany(state)
            }

            viewModel.chipsUiState.observe(viewLifecycleOwner) { state ->
                renderChips(state)
            }
        }
    }

    private fun initChips() {
        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS, LIKES, FAVORITES)
        binding.chips.setChips(chipTitles)
        binding.chips.selectChip(0)
        binding.chips.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                user?.let { user ->
                    when (chipIndex) {
                        0 -> viewModel.loadChipAll(authorId = user.userId)
                        1 -> viewModel.loadChipProjects(authorId = user.userId)
                        2 -> viewModel.loadChipIdeas(type = TYPE_IDEA, authorId = user.userId)
                        3 -> viewModel.loadChipLiked(pageSize = PAGE_SIZE)
                        4 -> viewModel.loadChipFavorites(pageSize = PAGE_SIZE)
                    }
                }
            }
        })
    }

    private fun initTopBar() {
        binding.topbarProfile.setRightIconClickListener {
            val direction = ProfileFragmentDirections.actionProfileFragmentToProfileBottomSheet()
            findNavController().navigate(direction)
        }
    }

    private fun initAdapter() {
        with(binding) {
            recyclerAll.layoutManager = LinearLayoutManager(context)
            recyclerProjects.layoutManager = LinearLayoutManager(context)
            recyclerIdeas.layoutManager = LinearLayoutManager(context)
            recyclerLikes.layoutManager = LinearLayoutManager(context)
            recyclerFavorites.layoutManager = LinearLayoutManager(context)

            recyclerAll.adapter = adapter
            recyclerProjects.adapter = adapter
            recyclerIdeas.adapter = adapter
            recyclerLikes.adapter = adapter
            recyclerFavorites.adapter = adapter
        }
    }

    private fun render(screenState: ProfileScreenState) {
        when (screenState) {
            is ProfileScreenState.Content -> {
                fillViews(screenState.profileInfo)
            }

            is ProfileScreenState.Error -> {
                showError()
            }
        }
    }

    private fun renderCompany(screenStateCompany: ProfileCompanyScreenState) {
        when (screenStateCompany) {
            is ProfileCompanyScreenState.Content -> {
                fillViewsCompany(screenStateCompany.profileCompany)
            }

            is ProfileCompanyScreenState.Error -> {
                showError()
            }
        }
    }

    private fun renderChips(chipsScreenState: ChipsScreenState) {
        when (chipsScreenState) {
            is ChipsScreenState.All -> showAllContent()

            is ChipsScreenState.Projects -> showUserProjects()

            is ChipsScreenState.Ideas -> showUserIdeas()

            is ChipsScreenState.Liked -> showUserLiked()

            is ChipsScreenState.Favorites -> showUserFavorites()

            is ChipsScreenState.Error -> showPlaceholder()

        }
    }

    private fun fillViews(profile: Profile) {
        with(binding) {
            name.text = profile.name
            description.text = profile.about
        }
    }

    private fun fillViewsCompany(company: ProfileCompany) {
        with(binding) {
            position.text = company.role
            companyName.text = company.name
            profileIn.isVisible = true
        }
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
        with(binding) {
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
            placeholderText.isVisible = true
        }
    }

    private fun showAllContent() {
        with(binding) {
            placeholderText.isVisible = false
            recyclerAll.isVisible = true
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
        }
    }

    private fun showUserProjects() {
        with(binding) {
            placeholderText.isVisible = false
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = true
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
        }
    }

    private fun showUserIdeas() {
        with(binding) {
            placeholderText.isVisible = false
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = true
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
        }
    }

    private fun showUserLiked() {
        with(binding) {
            placeholderText.isVisible = false
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = true
            recyclerFavorites.isVisible = false
        }
    }

    private fun showUserFavorites() {
        with(binding) {
            placeholderText.isVisible = false
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = true
        }
    }

    companion object {

        private const val ALL_CONTENT = "Всё"
        private const val PROJECT = "Проекты"
        private const val IDEAS = "Идеи"
        private const val LIKES = "Лайкнутые"
        private const val FAVORITES = "Избранное"
        private const val PAGE_SIZE = 10
        private const val TYPE_IDEA = "IDEA"
    }
}
