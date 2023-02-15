package com.example.googleadsadvancenew.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleadsadvancenew.adUtils.AdManagerSingle
import com.example.googleadsadvancenew.adUtils.Util
import com.example.googleadsadvancenew.databinding.ActivityMain3Binding
import com.google.android.gms.ads.MobileAds

class MainActivity3 : AppCompatActivity(), AdManagerSingle.CallBackAd {

    lateinit var mainBinding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        MobileAds.initialize(this@MainActivity3) {}

        AdManagerSingle.updateCallBack(this)

        mainBinding.btnLaod.setOnClickListener {
            mainBinding.bannerAds.loadBannerAds()
//            AdManagerSingle.loadAd()
        }

        mainBinding.btnShow.setOnClickListener {
            if (AdManagerSingle.isAdLoaded()) {
                Util.showToast(this, "Ads is Loaded ")
                AdManagerSingle.showAd(this@MainActivity3)
            } else {
                Util.showToast(this, "Ads is not Loaded ")
            }
        }



    }

    override fun adDismissed() {
        Util.showToast(this, "Ads is Dismissed ")
    }
}