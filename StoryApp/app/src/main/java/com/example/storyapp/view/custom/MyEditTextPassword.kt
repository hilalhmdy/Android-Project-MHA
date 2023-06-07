package com.example.storyapp.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class MyEditTextPassword : AppCompatEditText {

    private lateinit var checkListIcon: Drawable
    private lateinit var errorIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        checkListIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_check) as Drawable
        errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_error) as Drawable
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = null
                if (s.length > 7) {
                    showIconButton(2)
                }
                else if (s.length == 0) {
                    hideIconButton()
                }
                else if (s.length <= 7 ) {
                    error = "Invalid Password"
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }
    private fun showIconButton(idImage : Int) {
        if (idImage == 1) {
            setButtonDrawables(endOfTheText = errorIcon)
        }
        else if (idImage == 2) {
            setButtonDrawables(endOfTheText = checkListIcon)
        }
    }

    private fun hideIconButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }
}