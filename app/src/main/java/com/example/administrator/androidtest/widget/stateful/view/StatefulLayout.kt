package com.example.administrator.androidtest.widget.stateful.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import com.example.administrator.androidtest.widget.stateful.delegate.StatefulViewDelegate

class StatefulFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : FrameLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }
}

class StatefulLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : LinearLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }
}

class StatefulConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : ConstraintLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }
}
