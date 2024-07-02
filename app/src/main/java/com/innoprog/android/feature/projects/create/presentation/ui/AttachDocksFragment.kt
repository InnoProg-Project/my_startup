package com.innoprog.android.feature.projects.create.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Layout
import com.innoprog.android.databinding.FragmentProjectAttachDocksBinding
import com.innoprog.android.feature.projects.create.presentation.ui.adapter.DocumentsAdapter

class AttachDocksFragment : Layout() {
    private var _binding: FragmentProjectAttachDocksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectAttachDocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var documentAdapter: DocumentsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }


    private fun initRecycler() {
        documentAdapter = DocumentsAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.docksRecycler.adapter = documentAdapter
    }
}
