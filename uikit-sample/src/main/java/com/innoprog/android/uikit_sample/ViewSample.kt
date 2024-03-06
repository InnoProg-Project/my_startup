package com.innoprog.android.uikit_sample

import androidx.fragment.app.Fragment

enum class ViewSample {


    MyBottom {
        override fun newInstance(): Fragment {
            return MyBottom()
        }
    };

    abstract fun newInstance(): Fragment
}