package com.example.googleadsadvancenew

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.rewarded.RewardItem


class MainActivity : AppCompatActivity(), AdManagerNew.CallBackInterstitial,
    AdManagerNew.CallRewardedAd, AdManagerNew.CallBackRewardedInterstitialAd {

    private lateinit var adViewContainer: FrameLayout
    lateinit var backDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adViewContainer = findViewById(R.id.ad_view_container)

        backDialog = Dialog(this@MainActivity)
        if (backDialog.window != null) {
            backDialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //before
            backDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            backDialog.window!!.attributes?.windowAnimations = R.style.DialogAnimation5
        }

        backDialog.setContentView(R.layout.back_dialog_ads)

        NativeAdvanceNew.loadNativeAds(backDialog.findViewById(R.id.ad_view_native), this)

        findViewById<View>(R.id.loadOpenAds).setOnClickListener {
            if (MyApplication.appOpenManager != null) {
                MyApplication.appOpenManager.fetchAd()
            }
        }

        findViewById<View>(R.id.btnLoadBanner).setOnClickListener {
            Util.showToast(this, "calling banner")
            AdaptiveBannerLoad.loadBannerAd(adViewContainer, this@MainActivity)
        }

        findViewById<View>(R.id.btnInterstitial).setOnClickListener {
            Util.showToast(this, "calling Interstitial")
            AdManagerNew.loadInterstitialAd()
        }

        findViewById<View>(R.id.btnInterstitialShow).setOnClickListener {
            AdManagerNew.showInterstitial(this, this)
        }

        findViewById<View>(R.id.btnLoadRewardedAd).setOnClickListener {
            Util.showToast(this, "Load RewardedAd Request")
            AdManagerNew.loadRewardedAd()
        }

        findViewById<View>(R.id.showRewardedAd).setOnClickListener {
            AdManagerNew.showRewardedAd(this, this)
        }

        findViewById<View>(R.id.loadInit).setOnClickListener {
            Util.showToast(this, "Loading Rewarded Interstitial Load")
            AdManagerNew.loadRewardedInterstitialAd()
        }

        findViewById<View>(R.id.showInt).setOnClickListener {
            Util.showToast(this, "Show Rewarded Interstitial Load")
            AdManagerNew.showRewardedInterstitialAd(this, this)
        }

        findViewById<View>(R.id.showNative).setOnClickListener {
            backDialog.show()
        }

        findViewById<View>(R.id.go_next).setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        /*if (MyApplication.appOpenManager != null) {
            MyApplication.appOpenManager.fetchAd()
        }*/
    }

    override fun interstitialDismissedFullScreenContent() {

    }

    override fun interstitialFailedToShowFullScreenContent(adError: AdError?) {

    }

    override fun interstitialShowedFullScreenContent() {

    }

    override fun rewardedAdUserEarnedReward(rewardItem: RewardItem) {

    }

    override fun rewardedAdShowedFullScreenContent() {

    }

    override fun rewardedAdFailedToShowFullScreenContent(adError: AdError?) {

    }

    override fun rewardedAdDismissedFullScreenContent() {

    }

    override fun rewardedInterstitialAdUserEarnedRewardListener(rewardItem: RewardItem) {

    }

    override fun rewardedInterstitialAdShowedFullScreenContent() {

    }

    override fun rewardedInterstitialAdFailedToShowFullScreenContent(adError: AdError?) {

    }

    override fun rewardedInterstitialAdDismissedFullScreenContent() {

    }

}