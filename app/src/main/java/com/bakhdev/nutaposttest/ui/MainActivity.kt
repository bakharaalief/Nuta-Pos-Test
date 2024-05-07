package com.bakhdev.nutaposttest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.bakhdev.nutaposttest.R
import com.bakhdev.nutaposttest.databinding.ActivityMainBinding
import com.bakhdev.nutaposttest.ui.moneyIn.MoneyInFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //handle full status bar
//        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // savedInstanceState is null. This is to ensure that the fragment is added only once,
        // when the activity is first created. When a configuration change occurs
        // and the activity is recreated
        if (savedInstanceState == null) setUpFragment()
    }

    private fun setUpFragment() = supportFragmentManager.commit {
        setReorderingAllowed(true)
        add<MoneyInFragment>(R.id.fragment_container_view, MoneyInFragment.FRAG_TAG)
    }
}