package com.innoprog.android.uikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.innoprog.android.uikitsample.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            (applicationContext as SampleApp).switchTheme(checked)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                this.add(R.id.root_fragment_container, MainFragment())
            }
        }
    }
}