package com.innoprog.android.feature.projects.create.presentation.model

import android.net.Uri
import com.innoprog.android.feature.projects.create.domain.model.ProjectDirectionModel
import java.time.LocalDate

sealed class CreateProjectItemType(val viewType: Int) {
    class InputViewItem(viewType: Int, val label: String?, val hint: String, val input: String?) :
        CreateProjectItemType(viewType)

    class DocumentItem(viewType: Int, val url: String, val clickListener: (url: String) -> Unit) :
        CreateProjectItemType(viewType)

    class MediaItem(viewType: Int, val url: String, val clickListener: (url: String) -> Unit) :
        CreateProjectItemType(viewType)

    class AddMediaButtonItem(viewType: Int) : CreateProjectItemType(viewType)
    class AddLogoButtonItem(
        viewType: Int,
        val url: Uri?,
        val clickListener: () -> Unit
    ) : CreateProjectItemType(viewType)

    class InputDateView(
        viewType: Int,
        val deadLine: LocalDate?
    ) : CreateProjectItemType(viewType)

    class DirectionItem(
        viewType: Int,
        val direction: ProjectDirectionModel,
        val clickListener: (url: String) -> Unit
    ) :
        CreateProjectItemType(viewType)
}
