package com.example.googleadsadvancenew.adUtils

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import com.example.googleadsadvancenew.BuildConfig
import com.example.googleadsadvancenew.other.MyApplication
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

object AdManagerSingle {

    //Note! this id must b change not a Sample id interstitial Ads
    private var interstitialAdsSample: String = "ca-app-pub-3940256099942544/1033173712"
    private var interstitialAds: String = "ca-app-pub-3940256099942544/1033173712"
    private var mInterstitialAd: InterstitialAd? = null

    //Note! this id must b change not a Sample id  Video ads
    private var rewardedAdsSample = "ca-app-pub-3940256099942544/5224354917"
    private var rewardedAds = "ca-app-pub-3940256099942544/5224354917"
    private var mRewardedAd: RewardedAd? = null

    //Note! this id must b change not a Sample id on Ads
    private var rewardedInterstitialAdsSample: String = "ca-app-pub-3940256099942544/5354046379"
    private var rewardedInterstitialAds: String = "ca-app-pub-3940256099942544/5354046379"
    private var mRewardedInterstitialAd: RewardedInterstitialAd? = null

    private var callBack: CallBackAd? = null

    fun loadAd() {

        if (mRewardedAd == null && mRewardedInterstitialAd == null && mInterstitialAd == null) {

            Log.d("AdManagerSingle", "loaded Request Send")

            RewardedAd.load(
                MyApplication.context,
                getRewardedID(),
                getAdRequestNew(),
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("RewardedAd", "Error. -- ${adError}")
                        mRewardedAd = null
                        loadRewardedInterstitialAd()

                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Log.d("RewardedAd", "Ad was loaded Rewarded.")
                        mRewardedAd = rewardedAd
                    }
                })

        } else {
            Log.d("AdManagerSingle", "Already loaded ")
        }
    }

    fun showAd(activity: Activity) {

        if (mRewardedAd != null) {
            mRewardedAd?.show(activity, object : OnUserEarnedRewardListener {
                override fun onUserEarnedReward(p0: RewardItem) {

                }

            })

            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mRewardedAd = null
                    loadAd()
                    callBack?.adDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    callBack?.adDismissed()
                }

                override fun onAdShowedFullScreenContent() {

                }
            }

        } else if (mRewardedInterstitialAd != null) {

            mRewardedInterstitialAd?.show(activity, object : OnUserEarnedRewardListener {
                override fun onUserEarnedReward(p0: RewardItem) {

                }
            })

            mRewardedInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        mRewardedInterstitialAd = null
                        loadAd()
                        callBack?.adDismissed()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        callBack?.adDismissed()
                    }

                    override fun onAdShowedFullScreenContent() {

                    }
                }

        } else if (mInterstitialAd != null) {

            mInterstitialAd?.show(activity)

            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    loadAd()
                    callBack?.adDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    callBack?.adDismissed()
                }

                override fun onAdShowedFullScreenContent() {

                }
            }

        } else {
            Log.d("showAd", "The interstitial ad  ready yet.")
            loadAd()
        }
    }

    fun isAdLoaded(): Boolean {
        return if (mRewardedAd == null && mRewardedInterstitialAd == null && mInterstitialAd == null) {
            loadAd()
            false
        } else {
            true
        }
    }

    fun updateCallBack(callBackAd: CallBackAd) {
        callBack = callBackAd
    }

    private fun loadRewardedInterstitialAd() {

        if (mRewardedInterstitialAd == null) {

            RewardedInterstitialAd.load(
                MyApplication.context,
                getRewardedInterstitialID(),
                AdManagerNew.adRequest,
                object : RewardedInterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedInterstitialAd) {
                        Log.d("loadRewardedInterAd", "onAdLoaded")
                        mRewardedInterstitialAd = ad
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.d("loadRewardedInterAd", "onAdFailedToLoad -- ${loadAdError}")
                        mRewardedInterstitialAd = null
                        loadInterstitialAd()
                    }
                })

        } else {
            Log.d("loadRewardedInterAd", "Rewarded Interstitial is already loaded.")
        }

    }

    private fun loadInterstitialAd() {
        if (mInterstitialAd == null) {

            InterstitialAd.load(
                MyApplication.context,
                getInterstitialID(),
                getAdRequestNew(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("loadInterstitialAd", "onAdFailedToLoad  -- ${adError}")
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d("loadInterstitialAd", "onAdLoaded")
                        mInterstitialAd = interstitialAd
                    }
                })

        } else {
            Log.d(ContentValues.TAG, "InterstitialAd is already loaded.")
        }
    }

    private fun getRewardedID(): String {
        return if (BuildConfig.DEBUG) {
            rewardedAdsSample
        } else {
            rewardedAds
        }
    }

    private fun getRewardedInterstitialID(): String {
        return if (BuildConfig.DEBUG) {
            rewardedInterstitialAdsSample
        } else {
            rewardedInterstitialAds
        }
    }

    private fun getInterstitialID(): String {
        return if (BuildConfig.DEBUG) {
            interstitialAdsSample
        } else {
            interstitialAds
        }
    }

    private fun getAdRequestNew(): AdRequest {
        return AdRequest.Builder().build()
    }

    interface CallBackAd {
        fun adDismissed()
    }

}