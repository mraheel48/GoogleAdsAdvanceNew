package com.example.googleadsadvancenew.adUtils

import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.googleadsadvancenew.BuildConfig
import com.example.googleadsadvancenew.other.px2dp
import com.google.android.gms.ads.*

object MyBanner {

    private lateinit var mAdView: AdView
    val adRequest = AdRequest.Builder().build()
    val TAG = "BannerAd"

    // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
    private val AD_UNIT_ID_Test = "ca-app-pub-3940256099942544/6300978111"
    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

    private val bannerLoadOrNot: MutableLiveData<Boolean> = MutableLiveData()

    private fun getAdUnitId(): String {
        return if (BuildConfig.DEBUG) {
            AD_UNIT_ID_Test
        } else {
            AD_UNIT_ID
        }
    }

    fun loadBannerAd(
        adViewContainer: FrameLayout?,
        activity: AppCompatActivity?
    ): LiveData<Boolean> {

        bannerLoadOrNot.postValue(false)

        if (adViewContainer != null && activity != null) {

            adViewContainer.visibility = View.VISIBLE

            mAdView = AdView(activity)
            mAdView.adUnitId = getAdUnitId()
            adViewContainer.removeAllViews()
            adViewContainer.addView(mAdView)

            mAdView.setAdSize(adSize(adViewContainer, activity))
            mAdView.loadAd(adRequest)

            mAdView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.d(AdaptiveBannerLoad.TAG, "onAdLoaded")
                    bannerLoadOrNot.postValue(true)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                    Log.d(AdaptiveBannerLoad.TAG, "onAdFailedToLoad -- ${adError.responseInfo}")
                    bannerLoadOrNot.postValue(false)
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.d(AdaptiveBannerLoad.TAG, "onAdOpened")
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    Log.d(AdaptiveBannerLoad.TAG, "onAdClicked")
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                    Log.d(AdaptiveBannerLoad.TAG, "onAdClosed")
                }
            }

        }

        return bannerLoadOrNot
    }

    @Suppress("DEPRECATION")
    private fun adSize(adViewContainer: FrameLayout, activity: AppCompatActivity): AdSize {

        /*val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display?.getMetrics(outMetrics)

        val density = outMetrics.density

        var adWidthPixels = adViewContainer.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()*/

        val outMetrics = DisplayMetrics()

        var adWidthPixels = adViewContainer.width.toFloat()

        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            activity,
            adWidthPixels.px2dp.toInt()
        )

    }

}