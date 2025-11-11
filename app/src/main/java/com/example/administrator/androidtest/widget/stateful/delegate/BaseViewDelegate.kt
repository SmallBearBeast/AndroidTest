package com.example.administrator.androidtest.widget.stateful.delegate

import android.util.AttributeSet
import android.view.View
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful

@Suppress("UNCHECKED_CAST")
abstract class BaseViewDelegate<T : View> : IStateful {
    var attachedView: T? = null

    var isPressedEnable = false

    var isSelectedEnable = true

    override fun attachView(view: View?) {
        view ?: return
        attachedView = view as T?
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulView)
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_enable)) {
            isPressedEnable =
                typedArray.getBoolean(R.styleable.StatefulView_sf_pressed_enable, false)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_enable)) {
            isSelectedEnable =
                typedArray.getBoolean(R.styleable.StatefulView_sf_selected_enable, false)
        }

        if (isPressedEnable) {
            if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed)) {
                attachedView?.isPressed =
                    typedArray.getBoolean(R.styleable.StatefulView_sf_pressed, false)
            }
        }
        if (isSelectedEnable) {
            if (typedArray.hasValue(R.styleable.StatefulView_sf_selected)) {
                attachedView?.isSelected =
                    typedArray.getBoolean(R.styleable.StatefulView_sf_selected, false)
            }
        }
    }
}