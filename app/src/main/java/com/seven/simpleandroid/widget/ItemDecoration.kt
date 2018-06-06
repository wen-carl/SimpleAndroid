package com.seven.simpleandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecoration(val context: Context, var spanCount: Int = 0, var drawable: Drawable = context.getDrawable(android.R.drawable.divider_horizontal_bright)): RecyclerView.ItemDecoration() {

    init { }

    constructor(context: Context, spanCount: Int = 0, drawableId: Int) : this(context, spanCount, context.getDrawable(drawableId))

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        val manager = parent?.layoutManager
        if (manager != null) {
            when (manager) {
                is GridLayoutManager -> {
                    outRect?.top = drawable.intrinsicHeight
                    outRect?.left = drawable.intrinsicWidth
                }
                is LinearLayoutManager -> {
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        outRect?.top = drawable.intrinsicHeight
                    } else {
                        outRect?.left = drawable.intrinsicWidth
                    }
                }
                else -> {}
            }
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        var left: Int = 0
        var right: Int = 0
        var top: Int = 0
        var bottom: Int = 0
        val manager = parent?.layoutManager
        if (manager != null) {
            when (manager) {
                is GridLayoutManager -> {
                    val rowCount: Int
                    val columnCount: Int
                    var rowIndex: Int
                    var columnIndex: Int
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        columnCount = spanCount
                        rowCount = parent.childCount / spanCount + (if (parent.childCount % spanCount == 0) 0 else 1)

                        for (i in 0 until parent.childCount) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (0 >= index) continue

                            columnIndex = index % columnCount
                            rowIndex = index / columnCount
                            if (0 < columnIndex) {
                                left = child.left - drawable.intrinsicWidth
                                right = child.left
                                top = child.top
                                bottom = child.height + child.top

                                drawable.setBounds(left, top, right, bottom)
                                drawable.draw(c)
                            }

                            if (0 < rowIndex) {
                                top = child.top - drawable.intrinsicHeight
                                bottom = child.top
                                left = child.left
                                right = child.width + child.left

                                drawable.setBounds(left, top, right, bottom)
                                drawable.draw(c)
                            }
                        }
                    } else {
                        rowCount = spanCount
                        columnCount = parent.childCount / spanCount + (if (parent.childCount % spanCount == 0) 0 else 1)

                        for (i in 0 until parent.childCount) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (0 >= index) continue

                            rowIndex = index % rowCount
                            columnIndex = index / rowCount
                            if (0 < columnIndex) {
                                left = child.left - drawable.intrinsicWidth
                                right = child.left
                            }

                            if (0 < rowIndex) {
                                top = child.top - drawable.intrinsicHeight
                                bottom = child.top
                            }

                            drawable.setBounds(left, top, right, bottom)
                            drawable.draw(c)
                        }
                    }
                }
                is LinearLayoutManager -> {
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        left = parent.paddingLeft
                        right = parent.width - parent.paddingRight
                        for (i in 0 until parent.childCount) {
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
                        for (i in 0 until parent.childCount) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (0 >= index) continue

                            left = child.left - drawable.intrinsicWidth
                            right = child.left
                            drawable.setBounds(left, top, right, bottom)
                            drawable.draw(c)
                        }
                    }
                }
                else -> {}
            }
        }
    }
}