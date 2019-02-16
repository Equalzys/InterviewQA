package com.lookbi.baselibrary.utils

import android.widget.ImageView
import com.lookbi.baselibrary.imageloader.ImageUtil
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader

class AlbumMediaLoader : AlbumLoader {
    override fun load(imageView: ImageView?, url: String?) {

    }

    override fun load(imageView: ImageView?, albumFile: AlbumFile?) {
        ImageUtil.with(imageView?.getContext())
            .load(albumFile?.getPath())
            .into(imageView)
    }

}