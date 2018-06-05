package com.seven.simpleandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecoration(val context: Context, var drawable: Drawable = context.getDrawable(android.R.drawable.divider_horizontal_bright)): RecyclerView.ItemDecoration() {

    init { }

    constructor(context: Context, drawableId: Int) : this(context, context.getDrawable(drawableId))

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        val manager = parent?.layoutManager
        if (manager != null) {
            when (manager) {
                is LinearLayoutManager -> {
                    if (0 != parent.getChildAdapterPosition(view)) {
                        if (RecyclerView.VERTICAL == manager.layoutDirection) {
                            outRect?.top = drawable.intrinsicHeight
                        } else {
                            outRect?.left = drawable.intrinsicWidth
                        }
                    }
                }
                is GridLayoutManager -> {
                    if (0 != parent.getChildAdapterPosition(view)) {
                        outRect?.top = drawable.intrinsicHeight
                        outRect?.left = drawable.intrinsicWidth
                    }
                }
                else -> {}
            }
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        var left: Int
        var right: Int
        var top: Int
        var bottom: Int
        val manager = parent?.layoutManager
        if (manager != null) {
            when (manager) {
                is LinearLayoutManager -> {
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        left = parent.paddingLeft
                        right = parent.width - parent.paddingRight
                        for (i in 0 until manager.itemCount) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (0 >= index) continue

                            top = child.top - drawable.intrinsicHeight
                            bottom = child.top
                            drawable.setBounds(left, top, right, bottom)
                            drawable.draw(c)
                        }
                    } else {
                        top = parent.paddingTop
                        bottom = parent.height - parent.paddingBottom
                        for (i in 0 until manager.itemCount) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (0 == index) continue

                            left = child.left - drawable.intrinsicWidth
                            right = child.left
                            drawable.setBounds(left, top, right, bottom)
                            drawable.draw(c)
                        }
                    }
                }
                is GridLayoutManager -> {
                    // TODO()
                    top = parent.paddingTop
                    bottom = parent.height - parent.paddingBottom

                    for (i in 0 until manager.itemCount) {
                        val child = parent.getChildAt(i)
                        val index = parent.getChildAdapterPosition(child)
                        if (0 == index) continue

                        left = child.left - drawable.intrinsicWidth
                        right = child.left
                        drawable.setBounds(left, top, right, bottom)
                        drawable.draw(c)
                    }
                }
                else -> {}
            }
        }
    }
}