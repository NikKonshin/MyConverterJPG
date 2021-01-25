package com.example.myconverterjpg

import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.example.myconverterjpg.mvp.model.PictureHandler
import com.example.myconverterjpg.mvp.presenter.MainPresenter
import com.example.myconverterjpg.mvp.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

const val GALLERY_REQUEST = 1

class MainActivity : MvpAppCompatActivity(), MainView {
   private var selectedImage: Uri? = null
   private var bitmapPNG: Bitmap? = null

    private val presenter: MainPresenter by moxyPresenter {
        MainPresenter(AndroidSchedulers.mainThread(),PictureHandler())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> {
                selectedImage = data?.data
                presenter.update()
            }
        }
    }

    override fun init() {
        button_get_picture.setOnClickListener {
            val intent = Intent(ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST)
            presenter.countPicture++
        }

        image_view1.setImageURI(selectedImage)
        presenter.savePicturePng(selectedImage.toString())

        button_convert.setOnClickListener {
            bitmapPNG = presenter.getPicturePNG()?.bitmap
            presenter.update()
        }

        image_view2.setImageBitmap(bitmapPNG)
    }
}
