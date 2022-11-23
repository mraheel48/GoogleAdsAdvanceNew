package com.example.googleadsadvancenew.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.FrameLayout
import com.example.googleadsadvancenew.R
import com.example.googleadsadvancenew.adUtils.AdManagerNew
import com.example.googleadsadvancenew.adUtils.AdaptiveBannerLoad
import com.example.googleadsadvancenew.adUtils.NativeAdvanceNew
import com.example.googleadsadvancenew.adUtils.Util
import com.example.googleadsadvancenew.databinding.ActivityMainBinding
import com.example.googleadsadvancenew.other.MyApplication
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.rewarded.RewardItem

class MainActivity : AppCompatActivity(), AdManagerNew.CallBackInterstitial,
    AdManagerNew.CallRewardedAd, AdManagerNew.CallBackRewardedInterstitialAd {

    private lateinit var adViewContainer: FrameLayout
    lateinit var backDialog: Dialog
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        adViewContainer = findViewById(R.id.ad_view_container)

        backDialog = Dialog(this@MainActivity)
        if (backDialog.window != null) {
            backDialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //before
            backDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            backDialog.window!!.attributes?.windowAnimations = R.style.DialogAnimation5
        }

        backDialog.setContentView(R.layout.back_dialog_ads)

        NativeAdvanceNew.loadNativeAds(backDialog.findViewById(R.id.ad_view_native), this)

        mainBinding.loadOpenAds.setOnClickListener {
            if (MyApplication.appOpenManager != null) {
                MyApplication.appOpenManager.fetchAd()
            }
        }

        mainBinding.btnLoadBanner.setOnClickListener {
            Util.showToast(this, "calling banner")
            AdaptiveBannerLoad.loadBannerAd(adViewContainer, this@MainActivity)
        }

        mainBinding.btnInterstitial.setOnClickListener {
            Util.showToast(this, "calling Interstitial")
            AdManagerNew.loadInterstitialAd()
        }

        mainBinding.btnInterstitialShow.setOnClickListener {
            AdManagerNew.showInterstitial(this, this)
        }

        mainBinding.btnLoadRewardedAd.setOnClickListener {
            Util.showToast(this, "Load RewardedAd Request")
            AdManagerNew.loadRewardedAd()
        }

        mainBinding.showRewardedAd.setOnClickListener {
            AdManagerNew.showRewardedAd(this, this)
        }

        mainBinding.loadInit.setOnClickListener {
            Util.showToast(this, "Loading Rewarded Interstitial Load")
            AdManagerNew.loadRewardedInterstitialAd()
        }

        mainBinding.showInt.setOnClickListener {
            Util.showToast(this, "Show Rewarded Interstitial Load")
            AdManagerNew.showRewardedInterstitialAd(this, this)
        }

        mainBinding.showNative.setOnClickListener {
            backDialog.show()
        }

        mainBinding.goNext.setOnClickListener {
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