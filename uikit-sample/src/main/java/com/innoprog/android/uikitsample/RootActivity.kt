package com.innoprog.android.uikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.commit
import com.android.application.uikit_sample.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val themeSwitcher = findViewById<SwitchCompat>(R.id.theme_switcher)
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as SampleApp).switchTheme(checked)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                this.add(R.id.root_fragment_container, MainFragment())
            }
        }
    }
}