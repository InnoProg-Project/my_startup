package com.innoprog.android.feature.projects.create.presentation.model

sealed class CreateProjectItemType(val viewType: Int) {
    class InputView(viewType: Int, val hint: String, val input: String?) :
        CreateProjectItemType(viewType)

    class DocumentItem(viewType: Int, val url: String, val clickListener: (url: String) -> Unit) :
        CreateProjectItemType(viewType)

    class MediaItem(viewType: Int, val url: String, val clickListener: (url: String) -> Unit) :
        CreateProjectItemType(viewType)

    class AddMediaButtonItem(viewType: Int) : CreateProjectItemType(viewType)
    class AddLogoButtonItem(
        viewType: Int,
        val url: String?,
        val clickListener: (url: String) -> Unit
    ) : CreateProjectItemType(viewType) {

    }
}
