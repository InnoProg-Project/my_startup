package com.innoprog.android.feature.profile.profiledetails.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.profiledetails.di.DaggerProfileComponent
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.feature.profile.profiledetails.presentation.adapter.PublicationsRecyclerAdapter
import com.innoprog.android.uikit.InnoProgChipGroupView

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ProfileViewModel>()

    private var user: Profile? = null
    private var publications: ArrayList<FeedWrapper> = arrayListOf()

    private val publicationsAdapter: PublicationsRecyclerAdapter by lazy {
        PublicationsRecyclerAdapter(publications) { publication ->
            when (publication) {
                is FeedWrapper.Idea -> {
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToIdeaDetailsFragment(publication.id)
                    findNavController().navigate(action)
                }

                is FeedWrapper.News -> {
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToNewsDetailsFragment(publication.id)
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

        initAdapters()
    }

    @Suppress("NAME_SHADOWING")
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
        binding.chips.selectChip(INDEX_1)
        binding.chips.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                user?.let { user ->
                    when (chipIndex) {
                        INDEX_1 -> viewModel.loadChipAll(authorId = user.userId)
                        INDEX_2 -> viewModel.loadChipProjects(
                            type = TYPE_NEWS,
                            authorId = user.userId
                        )

                        INDEX_3 -> viewModel.loadChipIdeas(type = TYPE_IDEA, authorId = user.userId)
                        INDEX_4 -> viewModel.loadChipLiked(pageSize = PAGE_SIZE)
                        INDEX_5 -> viewModel.loadChipFavorites(pageSize = PAGE_SIZE)
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

    private fun initAdapters() {
        with(binding) {
            recyclerAll.adapter = publicationsAdapter
            recyclerProjects.adapter = publicationsAdapter
            recyclerIdeas.adapter = publicationsAdapter
            recyclerLikes.adapter = publicationsAdapter
            recyclerFavorites.adapter = publicationsAdapter
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

    @SuppressLint("NotifyDataSetChanged")
    private fun renderChips(chipsScreenState: ChipsScreenState) {
        when (chipsScreenState) {
            is ChipsScreenState.All -> {
                publications.clear()
                publications.addAll(chipsScreenState.content)
                publicationsAdapter.notifyDataSetChanged()
                showAllContent()
            }

            is ChipsScreenState.Projects -> {
                publications.clear()
                publications.addAll(chipsScreenState.projects)
                publicationsAdapter.notifyDataSetChanged()
                showUserProjects()
            }

            is ChipsScreenState.Ideas -> {
                publications.clear()
                publications.addAll(chipsScreenState.ideas)
                publicationsAdapter.notifyDataSetChanged()
                showUserIdeas()
            }

            is ChipsScreenState.Liked -> {
                publications.clear()
                publications.addAll(chipsScreenState.liked)
                publicationsAdapter.notifyDataSetChanged()
                showUserLiked()
            }

            is ChipsScreenState.Favorites -> {
                publications.clear()
                publications.addAll(chipsScreenState.favorites)
                publicationsAdapter.notifyDataSetChanged()
                showUserFavorites()
            }

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
        if (publicationsAdapter.publications.isEmpty()) {
            with(binding) {
                recyclerAll.isVisible = false
                recyclerProjects.isVisible = false
                recyclerIdeas.isVisible = false
                recyclerLikes.isVisible = false
                recyclerFavorites.isVisible = false
                placeholderText.isVisible = true
            }
        }
    }

    private fun showAllContent() {
        with(binding) {
            recyclerAll.isVisible = true
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
            showPlaceholder()
        }
    }

    private fun showUserProjects() {
        with(binding) {
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = true
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
            showPlaceholder()
        }
    }

    private fun showUserIdeas() {
        with(binding) {
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = true
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = false
            showPlaceholder()
        }
    }

    private fun showUserLiked() {
        with(binding) {
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = true
            recyclerFavorites.isVisible = false
            placeholderText.isVisible = false
            showPlaceholder()
        }
    }

    private fun showUserFavorites() {
        with(binding) {
            recyclerAll.isVisible = false
            recyclerProjects.isVisible = false
            recyclerIdeas.isVisible = false
            recyclerLikes.isVisible = false
            recyclerFavorites.isVisible = true
            showPlaceholder()
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
        private const val TYPE_NEWS = "NEWS"
        private const val INDEX_1 = 0
        private const val INDEX_2 = 1
        private const val INDEX_3 = 2
        private const val INDEX_4 = 3
        private const val INDEX_5 = 4
    }
}
