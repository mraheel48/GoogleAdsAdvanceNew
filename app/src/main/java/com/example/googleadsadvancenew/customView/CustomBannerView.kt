package com.example.googleadsadvancenew.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.googleadsadvancenew.BuildConfig
import com.example.googleadsadvancenew.databinding.BannerLayoutBinding
import com.example.googleadsadvancenew.other.px2dp
import com.google.android.gms.ads.*

class CustomBannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: BannerLayoutBinding
    private var mAdView: AdView
    private val adRequest = AdRequest.Builder().build()
    private val AD_UNIT_ID_Test = "ca-app-pub-3940256099942544/6300978111"
    private var AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    val TAG = "BannerAd"

    init {

        val mInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = BannerLayoutBinding.inflate(mInflater)

        binding.also {
            addView(it.root)
        }

        mAdView = AdView(context)

    }

    fun loadBannerAds() {
        mAdView.adUnitId = getAdUnitId()
        binding.adViewContainer.addView(mAdView)
        mAdView.setAdSize(adSize())
        mAdView.loadAd(adRequest)
    }

    private fun getAdUnitId(): String {
        return if (BuildConfig.DEBUG) {
            AD_UNIT_ID_Test
        } else {
            AD_UNIT_ID
        }
    }

    private fun adSize(): AdSize {
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            context,
            binding.root.width.px2dp.toInt()
        )
    }
}