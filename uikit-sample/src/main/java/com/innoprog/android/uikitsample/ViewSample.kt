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
            return InnoProgAvatarViewFragment()
        }
    },

    SMSCodeCustomView {
        override fun newInstance(): Fragment {
            return InnoProgSMSCodeViewFragment()
        }
    };

    abstract fun newInstance(): Fragment
}
