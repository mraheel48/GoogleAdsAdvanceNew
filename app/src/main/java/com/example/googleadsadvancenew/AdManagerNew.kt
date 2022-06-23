package com.example.googleadsadvancenew

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

//com.google.android.gms:play-services-ads:21.0.0
@SuppressLint("StaticFieldLeak")
object AdManagerNew {

    val mContext: Context = MyApplication.context

    //Note! this id must b change not a Sample id interstitial Ads
    private var interstitialAdsSample: String = "ca-app-pub-3940256099942544/1033173712"
    private var interstitialAds: String = "ca-app-pub-3940256099942544/1033173712"
    private var mInterstitialAd: InterstitialAd? = null
    val adRequest: AdRequest = AdRequest.Builder().build()
    private var callBackInterstitial: CallBackInterstitial? = null

    //Note! this id must b change not a Sample id  Video ads
    private var rewardedAdsSample = "ca-app-pub-3940256099942544/5224354917"
    private var rewardedAds = "ca-app-pub-3940256099942544/5224354917"
    private var mRewardedAd: RewardedAd? = null
    private var callRewardedAd: CallRewardedAd? = null

    //Note! this id must b change not a Sample id on Ads
    private var rewardedInterstitialAdsSample: String = "ca-app-pub-3940256099942544/5354046379"
    private var rewardedInterstitialAds: String = "ca-app-pub-3940256099942544/5354046379"
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private var onUserEarnedRewardListener: OnUserEarnedRewardListener? = null
    private var callRewardedInterstitial: CallBackRewardedInterstitialAd? = null

    init {
        if (BuildConfig.DEBUG) {
            interstitialAds = interstitialAdsSample
            rewardedAds = rewardedAdsSample
            rewardedInterstitialAds = rewardedInterstitialAdsSample
        }
    }

    fun loadInterstitialAd() {

        if (mInterstitialAd == null) {
            Log.d(TAG, "InterstitialAd was loaded Request Send")
            InterstitialAd.load(
                mContext,
                interstitialAds,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.message)
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mInterstitialAd = interstitialAd
                    }
                })

        } else {
            Log.d(TAG, "InterstitialAd is already loaded.")
        }
    }

    fun showInterstitial(activity: Activity, callBack: CallBackInterstitial) {
        if (mInterstitialAd != null) {
            callBackInterstitial = callBack
            mInterstitialAd?.show(activity)
            callBackInterstitialAd()
        } else {
            Log.d(TAG, "The interstitial ad  ready yet.")
            loadInterstitialAd()
        }
    }

    fun callBackInterstitialAd() {

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                callBackInterstitial?.interstitialDismissedFullScreenContent()
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                callBackInterstitial?.interstitialFailedToShowFullScreenContent(p0)
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                callBackInterstitial?.interstitialShowedFullScreenContent()
                mInterstitialAd = null
                loadInterstitialAd()
            }
        }
    }

    interface CallBackInterstitial {
        fun interstitialDismissedFullScreenContent()
        fun interstitialFailedToShowFullScreenContent(adError: AdError?)
        fun interstitialShowedFullScreenContent()
    }

    fun loadRewardedAd() {

        if (mRewardedAd == null) {

            Log.d(TAG, "loadRewardedAd request Send")

            RewardedAd.load(
                mContext,
                rewardedAds,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.message)
                        mRewardedAd = null
                        Log.d(TAG, "Error.")
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Log.d(TAG, "Ad was loaded Rewarded.")
                        mRewardedAd = rewardedAd
                    }
                })

        } else {
            Log.d(TAG, "RewardedAd is not already loaded.")
        }

    }

    fun showRewardedAd(activity: Activity, callBack: CallRewardedAd) {

        if (mRewardedAd != null) {

            callRewardedAd = callBack

            mRewardedAd?.show(activity) { it ->
                callRewardedAd?.rewardedAdUserEarnedReward(it)
                Log.d(TAG, "User earned the reward.")
            }

            callBackRewardedAd()

        } else {
            loadRewardedAd()
            Log.d(TAG, "The rewarded ad not ready yet.")
        }
    }

    fun callBackRewardedAd() {

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                callRewardedAd?.rewardedAdShowedFullScreenContent()
                Log.d(TAG, "onAdShowedFullScreenContent")

            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                callRewardedAd?.rewardedAdFailedToShowFullScreenContent(p0)
                Log.d(TAG, "onAdFailedToShowFullScreenContent")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                callRewardedAd?.rewardedAdDismissedFullScreenContent()
                mRewardedAd = null
                loadRewardedAd()
                Log.d(TAG, "onAdDismissedFullScreenContent")

            }
        }
    }

    interface CallRewardedAd {
        fun rewardedAdUserEarnedReward(rewardItem: RewardItem)
        fun rewardedAdShowedFullScreenContent()
        fun rewardedAdFailedToShowFullScreenContent(adError: AdError?)
        fun rewardedAdDismissedFullScreenContent()
    }

    fun loadRewardedInterstitialAd() {

        if (rewardedInterstitialAd == null) {

            Log.d(TAG, "RewardedInterstitialAd was loaded Request Send")

            // Use the test ad unit ID to load an ad.
            RewardedInterstitialAd.load(
                mContext,
                rewardedInterstitialAds,
                adRequest,
                object : RewardedInterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedInterstitialAd) {
                        rewardedInterstitialAd = ad
                        Log.d(TAG, "RewardedInterstitialAd was loaded")
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.d(TAG, "RewardedInterstitialAd was failed to load")
                    }
                })

        } else {
            Log.d(TAG, "Rewarded Interstitial is already loaded.")
        }
    }

    fun showRewardedInterstitialAd(activity: Activity, callBack: CallBackRewardedInterstitialAd) {

        if (rewardedInterstitialAd != null) {
            callRewardedInterstitial = callBack
            callRewardedInterstitialAd()
            if (onUserEarnedRewardListener != null) {
                rewardedInterstitialAd?.show(activity, onUserEarnedRewardListener!!)
            }
        } else {
            loadRewardedInterstitialAd()
        }
    }

    fun callRewardedInterstitialAd() {

        onUserEarnedRewardListener = OnUserEarnedRewardListener {
            Log.d(TAG, "onUserEarnedReward")
            callRewardedInterstitial?.rewardedInterstitialAdUserEarnedRewardListener(it)
        }

        rewardedInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.i(TAG, "onAdFailedToShowFullScreenContent")
                callRewardedInterstitial?.rewardedInterstitialAdFailedToShowFullScreenContent(
                    adError
                )
            }

            override fun onAdShowedFullScreenContent() {
                Log.i(TAG, "onAdShowedFullScreenContent")
                callRewardedInterstitial?.rewardedInterstitialAdShowedFullScreenContent()
            }

            override fun onAdDismissedFullScreenContent() {
                Log.i(TAG, "onAdDismissedFullScreenContent")
                callRewardedInterstitial?.rewardedInterstitialAdDismissedFullScreenContent()
                rewardedInterstitialAd = null
                loadRewardedInterstitialAd()
            }
        }
    }

    interface CallBackRewardedInterstitialAd {
        fun rewardedInterstitialAdUserEarnedRewardListener(rewardItem: RewardItem)
        fun rewardedInterstitialAdShowedFullScreenContent()
        fun rewardedInterstitialAdFailedToShowFullScreenContent(adError: AdError?)
        fun rewardedInterstitialAdDismissedFullScreenContent()
    }
}