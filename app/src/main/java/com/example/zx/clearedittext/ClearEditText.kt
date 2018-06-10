package com.example.zx.clearedittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText

class ClearEditText : EditText {
    private var clearImg: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    private fun init() {
        clearImg = ContextCompat.getDrawable(context, R.drawable.ic_clear)
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
}