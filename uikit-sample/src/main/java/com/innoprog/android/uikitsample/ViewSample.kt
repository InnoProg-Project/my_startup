package com.innoprog.android.uikitsample

import androidx.fragment.app.Fragment

enum class ViewSample {
    MyBottom {
        override fun newInstance(): Fragment {
            return MyBottomFragment()
        }
    },

    LikeView {
        override fun newInstance(): Fragment {
            return InnoProgLikeViewFragment()
        }
    },
          
    AvatarCustomView {
        override fun newInstance(): Fragment {
            return InnoProgAvatarViewFragment()
        }
    };

    abstract fun newInstance(): Fragment
}
