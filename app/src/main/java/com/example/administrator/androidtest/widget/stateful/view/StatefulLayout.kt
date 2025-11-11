package com.example.administrator.androidtest.widget.stateful.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import com.example.administrator.androidtest.widget.stateful.delegate.StatefulViewDelegate

open class StatefulFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : FrameLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        val superState = super.onSaveInstanceState()
        bundle.putParcelable("super_state", superState)
        onSaveInstanceState(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val superState: Parcelable? = state.getParcelable("super_state")
            super.onRestoreInstanceState(superState)
            onRestoreInstanceState(state)
        }
    }
}

open class StatefulLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : LinearLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        val superState = super.onSaveInstanceState()
        bundle.putParcelable("super_state", superState)
        onSaveInstanceState(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val superState: Parcelable? = state.getParcelable("super_state")
            super.onRestoreInstanceState(superState)
            onRestoreInstanceState(state)
        }
    }
}

open class StatefulConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : ConstraintLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        val superState = super.onSaveInstanceState()
        bundle.putParcelable("super_state", superState)
        onSaveInstanceState(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val superState: Parcelable? = state.getParcelable("super_state")
            super.onRestoreInstanceState(superState)
            onRestoreInstanceState(state)
        }
    }
}
