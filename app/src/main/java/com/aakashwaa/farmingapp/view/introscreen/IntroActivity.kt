package com.aakashwaa.farmingapp.view.introscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.aakashwaa.farmingapp.R
import com.aakashwaa.farmingapp.adapter.IntroAdapter
import com.aakashwaa.farmingapp.model.data.IntroData
import com.aakashwaa.farmingapp.view.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val introSliderAdapter = IntroAdapter(
        listOf(
            IntroData(
                "Welcome to the\n\bForkissan\b",
                "Best Guide and Helper for any Farmer. Provides various features at one place!",
                R.drawable.intro_first
            ),
            IntroData(
                "Ai Powered Disease Detection and Remedy Recomendation",
                "Forkissan Ai cam",
                R.drawable.intro_help
            )

        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        sliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        if(sliderViewPager.currentItem + 1 == introSliderAdapter.itemCount){
            Log.d("IntroActivity", sliderViewPager.currentItem.toString())
            Log.d("IntroActivity", introSliderAdapter.itemCount.toString())
            nextBtn.text = "Get Started"
        } else{
            nextBtn.text = "Next"
        }

        nextBtn.setOnClickListener {
            if (sliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                sliderViewPager.currentItem += 1
//                Toast.makeText(this, "Current: ${sliderViewPager.currentItem}", Toast.LENGTH_SHORT).show()
                nextBtn.text = "Next"
                if(sliderViewPager.currentItem + 1 == introSliderAdapter.itemCount){
                    Log.d("IntroActivity", sliderViewPager.currentItem.toString())
                    Log.d("IntroActivity", introSliderAdapter.itemCount.toString())
                    nextBtn.text = "Get Started"
                }
            } else {

                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("firstTime", false)
                editor.apply()
                finish()
            }
        }
        skipIntro.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstTime", false)
            editor.apply()
            finish()
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }

            sliderballs_container.addView(indicators[i])


        }

    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = sliderballs_container.childCount
        for (i in 0 until childCount) {
            val imageView = sliderballs_container.get(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }

        if(index == introSliderAdapter.itemCount - 1){
            nextBtn.text = "Get Started"
        } else{
            nextBtn.text = "Next"

        }
    }
}