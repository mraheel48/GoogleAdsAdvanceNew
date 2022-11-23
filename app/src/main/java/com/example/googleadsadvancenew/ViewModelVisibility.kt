package com.example.googleadsadvancenew

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelVisibility : ViewModel() {

    private val viewVisibility: MutableLiveData<Boolean> = MutableLiveData()

    fun checkViewVisible(view: View): LiveData<Boolean> {

        viewVisibility.postValue(true)

        if (view.visibility == View.VISIBLE) {
            viewVisibility.postValue(true)
        } else {
            viewVisibility.postValue(false)
        }

        return viewVisibility
    }

}