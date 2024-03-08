package com.innoprog.android.uikitsample

import androidx.fragment.app.Fragment

enum class ViewSample {
    MyBottom {
        override fun newInstance(): Fragment {
            return MyBottomFragment()
        }
    },

    AvatarCustomView {
        override fun newInstance(): Fragment {
            return AvatarCustomViewFragment()
        }
    };

    abstract fun newInstance(): Fragment
}
