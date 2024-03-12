package com.innoprog.android.uikitsample

import androidx.fragment.app.Fragment

enum class ViewSample {
    MyBottom {
        override fun newInstance(): Fragment {
            return MyBottomFragment()
        }
    },
    InputView {
        override fun newInstance(): Fragment {
            return InnoProgInputViewFragment()
        }
    };

    abstract fun newInstance(): Fragment
}
