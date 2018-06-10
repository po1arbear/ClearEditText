package com.example.zx.clearedittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat


class ClearEditText : EditText {
    private var clearImg: Drawable? = null
    private var drawableWidth= 0f
    private var drawableHeight = 0f

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(context,attributeSet)
    }

    private fun init(context: Context,attributeSet: AttributeSet?) {
        val typeArray  = context.obtainStyledAttributes(attributeSet,R.styleable.ClearEditText)
        var clearDrawable = typeArray.getDrawable(R.styleable.ClearEditText_clearDrawable)
        drawableWidth = typeArray.getDimension(R.styleable.ClearEditText_drawableWidth,0f)
        drawableHeight = typeArray.getDimension(R.styleable.ClearEditText_drawableHeight,0f)
        clearImg =zoomDrawable(clearDrawable,drawableWidth,drawableHeight)

//        clearImg = ContextCompat.getDrawable(context, R.drawable.ic_clear)
        addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
                } else {
                    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearImg, null)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN){
            if(event.x>width-totalPaddingRight &&event.x<width-paddingRight){
                setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    private fun zoomDrawable(drawable: Drawable, w: Float, h: Float): Drawable {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldBmp = drawableToBitmap(drawable)
        val matrix = Matrix()
        val scaleWidth = w / width
        val scaleHeight = h / height
        matrix.postScale(scaleWidth, scaleHeight)
        val newBmp = Bitmap.createBitmap(oldBmp, 0, 0, width, height,
                matrix, true)
        return BitmapDrawable(null, newBmp)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE)
            Bitmap.Config.ARGB_8888
        else
            Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }
}