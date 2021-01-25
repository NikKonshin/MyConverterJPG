package com.example.myconverterjpg.mvp.presenter

import android.util.Log
import com.example.myconverterjpg.mvp.model.Picture
import com.example.myconverterjpg.mvp.model.PictureHandler
import com.example.myconverterjpg.mvp.view.MainView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class MainPresenter(
    private val mainThread: Scheduler,
    private val pictureHandler: PictureHandler
) : MvpPresenter<MainView>() {
    var countPicture = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun getPicturePNG(): Picture? {
        return pictureHandler.picture
    }

    fun savePicturePng(uri: String) {
        pictureHandler.savePicturePng(uri, countPicture)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread)
            .subscribe({
            }, { e ->
                Log.v("", "e savePicturePng(uri: String) $e")
            })
    }

    fun update() {
        viewState.init()
    }


}