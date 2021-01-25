package com.example.myconverterjpg.mvp.model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.net.toUri
import com.example.myconverterjpg.MyApplication
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Completable
import java.io.ByteArrayOutputStream

class PictureHandler {
    private val context = MyApplication().getAppContext()
    private var imageName: String? = null

    var picture: Picture? = null
        private set

    fun savePicturePng(image: String, count: Int): Completable =
        Completable.create { emitter ->
            try {
                val uri = image.toUri()
                val bitmap = Bitmap
                    .createBitmap(Picasso.get().load(uri).get())
                val stream = ByteArrayOutputStream()

                Log.v("Picture", "bitmap.toDrawable(R.drawable.\"picture${count}.png\")")
                imageName = "picture${count}.png"
                context?.openFileOutput(imageName, Context.MODE_PRIVATE)
                    .use {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        picture = Picture(bitmap)
                        it?.write(stream.toByteArray())
                    }
                emitter.onComplete()
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
}