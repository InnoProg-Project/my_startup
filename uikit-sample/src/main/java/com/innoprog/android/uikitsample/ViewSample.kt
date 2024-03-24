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
    },
    ChipGroupView {
        override fun newInstance(): Fragment {
            return InnoProgChipGroupFragment()
        }
    },

    ButtonView {
        override fun newInstance(): Fragment {
            return InnoProgButtonViewFragment()
        }
    },

    SMSCodeCustomView {
        override fun newInstance(): Fragment {
            return InnoProgSMSCodeViewFragment()
        }
    };

    abstract fun newInstance(): Fragment
}
