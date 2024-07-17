package com.innoprog.android.feature.projects.create.presentation.ui.viewholders

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemLoadingLogoBinding

class LogoViewHolder(val binding: ItemLoadingLogoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(url: Uri?, clickListener: () -> Unit) {
        if (url != null) {
            binding.ivLogo.setImageURI(url)
        }
        binding.btLoadingLogo.setOnClickListener {
            clickListener.invoke()
        }
    }
}
