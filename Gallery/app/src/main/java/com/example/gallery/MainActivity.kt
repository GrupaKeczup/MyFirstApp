package com.example.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.viewpagerindicator.CirclePageIndicator
import java.util.ArrayList

// https://demonuts.com/kotlin-image-slider/

class MainActivity : AppCompatActivity() {
    private var imageModelArrayList : ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.alcedo,
                                         R.drawable.dog,
                                         R.drawable.hedgehog,
                                         R.drawable.mammals,
                                         R.drawable.rabbit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        init()
    }

    private fun populateList(): ArrayList<ImageModel> {
        val list = ArrayList<ImageModel>()

        for (imageItem in myImageList) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(imageItem)
            list.add(imageModel)
        }

        return list
    }

    private fun init() {
        mPager = findViewById<ViewPager>(R.id.pager)
        mPager!!.adapter = SlidingImageAdapter(this@MainActivity, this.imageModelArrayList!!)

        val indicator = findViewById<CirclePageIndicator>(R.id.indicator)

        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 5 * density

        NUM_PAGES = imageModelArrayList!!.size

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}

            override fun onPageScrollStateChanged(pos: Int) {}
        })
    }

    companion object {
        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }
}