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

    TopBarView {
        override fun newInstance(): Fragment {
            return InnoProgTopBarViewFragment()
        }
    },

    ButtonView {
        override fun newInstance(): Fragment {
            return InnoProgButtonViewFragment()
        }
    };

    abstract fun newInstance(): Fragment
}
