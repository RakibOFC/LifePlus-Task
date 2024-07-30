package com.rakibofc.lifeplustask.ui.customlayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView

class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val originalWidth = MeasureSpec.getSize(widthMeasureSpec)
        val calculatedHeight = (originalWidth * 3) / 2
        val calculatedHeightSpec =
            MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, calculatedHeightSpec)
    }
}
