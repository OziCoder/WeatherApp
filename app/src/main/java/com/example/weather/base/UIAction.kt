package com.example.weather.base

import android.view.View

typealias UIActionEmitter = (UIAction) -> Unit

open class UIAction(open val sender: View? = null)

open class ClickAction(
    override val sender: View? = null,
) : UIAction(sender)
