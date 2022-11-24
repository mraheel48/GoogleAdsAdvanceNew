package com.example.googleadsadvancenew.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowMetricsCalculator
import com.example.googleadsadvancenew.ViewModelVisibility
import com.example.googleadsadvancenew.adUtils.MyBanner
import com.example.googleadsadvancenew.databinding.ActivityMain2Binding
import com.example.googleadsadvancenew.other.px2dp
import com.google.android.gms.ads.*

class MainActivity2 : AppCompatActivity() {

    lateinit var mainBinding: ActivityMain2Binding
    private var viewModel: ViewModelVisibility? = null

    private lateinit var windowInfoTracker: WindowInfoTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        MobileAds.initialize(this@MainActivity2) {}

        windowInfoTracker = WindowInfoTracker.getOrCreate(this@MainActivity2)

        mainBinding.loadOpenAds.setOnClickListener {
            mainBinding.adViewContainer.visibility = View.GONE
        }

        mainBinding.btnLoadBanner.setOnClickListener {

            loadBanner()

            // Step 1: Create an inline adaptive banner ad size using the activity context.
            /*val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this, 360)

            // Step 2: Create banner using activity context and set the inline ad size and
            // ad unit ID.
            val bannerView = AdManagerAdView(this)
            bannerView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

            // Note that this sets both the adaptive ad size for backfill inventory as well
            // as the supported reservation sizes.
            bannerView.setAdSizes(adSize, AdSize.BANNER)

            // Step 3: Load an ad.
            val adRequest = AdManagerAdRequest.Builder().build()
            bannerView.loadAd(adRequest)

            mainBinding.adViewContainer.addView(bannerView)*/

        }

        mainBinding.btnInterstitial.setOnClickListener {


        }

    }

    @Suppress("UNUSED_VARIABLE")
    fun calculateW(): Float {

        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this@MainActivity2)
        val currentBounds = windowMetrics.bounds
        val width = currentBounds.width()
        val height = currentBounds.height()

        return width.toFloat()

    }

    fun bannerAdsLoadObserve() {

        MyBanner.loadBannerAd(mainBinding.adViewContainer, this@MainActivity2)
            .observe(this, object : Observer<Boolean> {
                override fun onChanged(t: Boolean?) {
                    if (t != null) {
                        if (t) {
                            Log.d("myBanner", "Banner is load")
                        } else {
                            Log.d("myBanner", "Banner is not  load")
                        }
                    }
                }
            })
    }

    var adView: AdView? = null

    private fun loadBanner() {

        adView = AdView(this@MainActivity2)
        adView?.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        mainBinding.adViewContainer.visibility = View.VISIBLE
        mainBinding.adViewContainer.removeAllViews()
        mainBinding.adViewContainer.addView(adView)

        var adWidthPixels = mainBinding.adViewContainer.width.toFloat()
        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0f) {
            adWidthPixels = calculateW()
        }

        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            this,
            adWidthPixels.px2dp.toInt()
        )

        adView?.setAdSize(adSize)

        adView?.loadAd(AdRequest.Builder().build())

        adView?.adListener = object : AdListener() {

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("myBanner", "onAdLoaded")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.d("myBanner", "Failed")

            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("myBanner", "onAdOpened")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("myBanner", "onAdClicked")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d("myBanner", "onAdClosed")
            }
        }

    }

}