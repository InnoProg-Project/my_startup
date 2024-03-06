package com.innoprog.android.uikit_sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.android.application.uikit_sample.R

class MainFragment : Fragment() {

    private var items = ViewSample.values()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_sample_view)
        val adapter = ViewAdapter(items) { sampleType ->
            parentFragmentManager.commit {
                replace(
                    R.id.root_fragment_container,
                    sampleType.newInstance(),
                    sampleType.name
                )
                addToBackStack(sampleType.name)
            }
        }
        recyclerView.adapter = adapter
    }
}