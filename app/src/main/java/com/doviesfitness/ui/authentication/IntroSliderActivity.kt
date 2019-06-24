package com.doviesfitness.ui.authentication


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Html
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.doviesfitness.R
import com.doviesfitness.ui.authentication.login.LoginActivity
import com.doviesfitness.ui.authentication.signup.SignupActivity
import com.doviesfitness.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_intro_slider.*
import java.util.*

class IntroSliderActivity : BaseActivity(), View.OnClickListener {

    private var introViewPager: ViewPager? = null
    private var introViewPagerAdapter: IntroScreenViewPagerAdapter? = null
    private var introBullets: Array<TextView>? = null
    private var introBulletsLayout: LinearLayout? = null
    private var introSliderLayouts: IntArray? = null
    private var mLastClickTime: Long = 0
    var currentPage = 0
    val NUM_PAGES = 4
    val DELAY_MS: Long = 2000//delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_intro_slider)
        initialize()

    }

    private fun initialize() {

        btn_login.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        introViewPager = intro_view_pager
        introBulletsLayout = intro_bullets
        //Get the intro slides
        introSliderLayouts = intArrayOf(
            R.layout.intro_screen_1,
            R.layout.intro_screen_2,
            R.layout.intro_screen_3
        )
        makeIIntroBullets(0)

        introViewPagerAdapter = IntroScreenViewPagerAdapter()
        introViewPager!!.adapter = introViewPagerAdapter
        introViewPager!!.addOnPageChangeListener(introViewPagerListener)


        /*After setting the adapter use the timer */
        val handler = Handler()
        val Update = Runnable {

            if (currentPage == NUM_PAGES - 1) {
                currentPage = 0
            }
            introViewPager!!.setCurrentItem(currentPage++, true)
        }

        val timer = Timer() // This will create a new Thread
        timer.schedule(object : TimerTask() { // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    private fun makeIIntroBullets(currentPage: Int) {
        val arraySize = introSliderLayouts!!.size
        introBullets = Array<TextView>(arraySize) {
            textboxInit()
        }
        val colorsActive = resources.getIntArray(R.array.array_intro_bullet_active)
        val colorsInactive = resources.getIntArray(R.array.array_intro_bullet_inactive)


        introBulletsLayout!!.removeAllViews()
        for (i in 0 until introBullets!!.size) {
            introBullets!![i] = TextView(this)
            introBullets!![i].text = Html.fromHtml("&#9679;")
            introBullets!![i].setTextSize(15F)
            introBullets!![i].setTextColor(colorsInactive[currentPage])
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            param.setMargins(8, 0, 8, 0)
            introBullets!![i].layoutParams = param
            introBulletsLayout!!.addView(introBullets!![i])
        }
        if (introBullets!!.size > 0)
            introBullets!![currentPage].setTextColor(colorsActive[currentPage])
    }

    private fun textboxInit(): TextView {
        return TextView(applicationContext)
    }

    private fun getItem(i: Int): Int {
        return introViewPager!!.getCurrentItem() + i
    }

    private var introViewPagerListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            makeIIntroBullets(position)
            /*Based on the page position change the button text*/
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            //Do nothing for now
        }

        override fun onPageScrollStateChanged(arg0: Int) {
            //Do nothing for now
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime()

                }
               intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }
            R.id.btn_signup -> {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime()

                }
                intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }

        }

    }

    private fun preventDoubleClick() {


    }


    inner class IntroScreenViewPagerAdapter : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
            val view = layoutInflater.inflate(introSliderLayouts!![position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return introSliderLayouts!!.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}
