package com.example.googleadsadvancenew

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

object NativeAdvanceNew {

    private lateinit var mAdView: AdView
    var currentNativeAd: NativeAd? = null
    val adRequest = AdRequest.Builder().build()
    val TAG = "BannerAd"

    // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
    private val AD_UNIT_ID_Test = "ca-app-pub-3940256099942544/2247696110"
    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"

    fun loadNativeAds(adViewContainer: FrameLayout?, activity: AppCompatActivity?) {

        if (adViewContainer != null && activity != null) {
            val builder = AdLoader.Builder(activity, AD_UNIT_ID_Test)
                .forNativeAd { nativeAd ->
                    // Assumes that your ad layout is in a file call native_ad_layout.xml
                    // in the res/layout folder
                    val adView = View.inflate(activity, R.layout.ad_unified, null) as NativeAdView
                    // This method sets the text, images and the native ad, etc into the ad
                    // view.
                    populateUnifiedNativeAdView(nativeAd, adView)
                    // Assumes you have a placeholder FrameLayout in your View layout
                    // (with id ad_frame) where the ad is to be placed.
                    adViewContainer.removeAllViews()
                    adViewContainer.addView(adView)
                }.withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                    }
                }).build()

            builder.loadAd(AdManagerAdRequest.Builder().build())
        }


    }

    private fun populateUnifiedNativeAdView(
        nativeAd: NativeAd,
        adView: NativeAdView,
    ) {
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        if (currentNativeAd != null) {
            currentNativeAd!!.destroy()
        }
        currentNativeAd = nativeAd
        // Set the media view.

        val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
        adView.mediaView = mediaView

        // Call the NativeAdView's setNativeAd method to register the
        // NativeAdObject.
        adView.setNativeAd(nativeAd)
    }
}