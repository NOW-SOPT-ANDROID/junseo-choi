package com.sopt.now.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import jp.wasabeef.transformers.coil.BlurTransformation

@BindingAdapter("imageUrl")
fun loadImage(
    view: ImageView,
    imageUrl: String?,
) {
    view.load(imageUrl)
}

@BindingAdapter("imageUrl", "cornerRadius")
fun loadRoundedCornerImage(
    view: ImageView,
    imageUrl: String?,
    cornerRadius: Float,
) {
    loadCustomImage(view, imageUrl, RoundedCornersTransformation(cornerRadius))
}

@BindingAdapter("imageUrl", "blurRadius")
fun loadBlurredImage(
    view: ImageView,
    imageUrl: String?,
    blurRadius: Int,
) {
    loadCustomImage(view, imageUrl, BlurTransformation(view.context, blurRadius))
}

private fun loadCustomImage(
    view: ImageView,
    imageUrl: String?,
    transformation: Transformation,
) {
    imageUrl?.let {
        view.load(it) {
            listener(
                onSuccess = { _, _ ->
                    view.load(imageUrl) {
                        crossfade(true)
                        transformations(transformation)
                    }
                },
                onError = { _, _ ->
                    Log.e("BindingAdapter", "Failed to load image")
                },
            )
        }
    } ?: run {
        Log.e("BindingAdapter", "Failed to load image")
    }
}

@BindingAdapter("isVisible")
fun setVisibility(
    view: View,
    isVisible: Boolean,
) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}
