package com.lookbi.baselibrary.imageloader

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lookbi.baselibrary.utils.LogUtil
import com.lookbi.baselibrary.views.CircleImageView
import com.lookbi.baselibrary.views.CustomImageView
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File

class ImageUtil {


    companion object {
        @JvmStatic
        fun with(context: Context?): Load {
            return Load(context)
        }
    }

    class Load {
        private var context: Context? = null

        constructor(context: Context?) {
            this.context = context;
        }

        fun load(url: String?): Into {
            return Into(context, url)
        }

        fun load(resId: Int?): Into {
            return Into(context, resId)
        }

        fun load(url: File?): Into {
            return Into(context, url)
        }
    }

    class Into {
        private var placeResid: Int = 0
        private var errResid: Int = 0
        private var url: String? = null
        private var file: File? = null
        private var resId: Int? = 0
        private var context: Context? = null
        private var options: RequestOptions? = null

        constructor(context: Context?, url: String?) {
            this.context = context
            this.url = url
        }

        constructor(context: Context?, file: File?) {
            this.context = context
            this.file = file
        }

        constructor(context: Context?, resId: Int?) {
            this.context = context
            this.resId = resId
        }

        fun placeholder(placeResid: Int): Into {
            this.placeResid = placeResid
            return this
        }

        fun error(errResid: Int): Into {
            this.errResid = errResid
            return this
        }

        fun options(options: RequestOptions?): Into {
            this.options = options
            return this
        }

        fun into(imageView: ImageView?) {
            if (context == null) {
                LogUtil.e("ImageUtil-context =null");
                return
            }
            if (url == null) {
                if (file == null) {
                    if (resId == 0) {
                        LogUtil.e("ImageUtil - url or file or resId is null");
                        return
                    }
                }
            }
            if (TextUtils.isEmpty(url)) {
                if (file == null) {
                    if (resId == 0) {
                        LogUtil.e("ImageUtil - url or file or resId is isEmpty");
                        return
                    }
                }
            }
            if (imageView == null) {
                LogUtil.e("ImageUtil-imageView =null");
                return
            }
            if (options == null) {
                options = RequestOptions()
            }
            try {
                if (imageView is CircleImageView) {
                    options!!.dontAnimate()
                        .centerCrop()
                    if (placeResid != 0) {
                        options!!.placeholder(placeResid)
                    }
                    if (errResid != 0) {
                        options!!.error(placeResid)
                    }
                    if (TextUtils.isEmpty(url)) {
                        if (resId != 0) {
                            Glide.with(context!!)
                                .load(resId)
                                .apply(options!!)
                                .into(imageView)
                        } else {
                            Glide.with(context!!)
                                .load(file)
                                .apply(options!!)
                                .into(imageView)
                        }

                    } else {
                        Glide.with(context!!)
                            .load(url)
                            .apply(options!!)
                            .into(imageView)

                    }


                } else {
                    if (placeResid != 0) {
                        options!!.placeholder(placeResid)
                    }
                    if (errResid != 0) {
                        options!!.error(placeResid)
                    }
                    if (imageView is RoundedImageView) {
                        if (TextUtils.isEmpty(url)) {
                            if (resId != 0) {
                                Glide.with(context!!)
                                    .load(resId)
                                    .apply(options!!)
                                    .into(imageView)
                            } else {
                                Glide.with(context!!)
                                    .load(file)
                                    .apply(options!!)
                                    .into(imageView)
                            }

                        } else {
                            Glide.with(context!!)
                                .load(url)
                                .apply(options!!)
                                .into(imageView)
                        }
                        return
                    }

                    if (TextUtils.isEmpty(url)) {
                        if (resId != 0) {
                            Glide.with(context!!)
                                .load(resId)
                                .apply(options!!)
                                .into(imageView)
                        } else {
                            Glide.with(context!!)
                                .load(file)
                                .apply(options!!)
                                .into(imageView)
                        }

                    } else {
                        Glide.with(context!!)
                            .load(url)
                            .apply(options!!)
                            .into(imageView)

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }



            placeResid = 0
            errResid = 0
            url = ""
            file = null
            resId = 0
            context = null
            options = null
        }

    }


}