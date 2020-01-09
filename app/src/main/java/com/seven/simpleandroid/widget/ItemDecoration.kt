package com.seven.simpleandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.*
import kotlin.math.*


class ItemDecoration(val context: Context, var spanCount: Int = 0, val drawable: Drawable? = context.getDrawable(android.R.drawable.divider_horizontal_bright)) : RecyclerView.ItemDecoration() {

    init {
    }

    constructor(context: Context, spanCount: Int = 0, drawableId: Int) : this(context, spanCount, context.getDrawable(drawableId)!!)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (null == drawable) return

        val manager = parent.layoutManager
        if (manager != null) {
            val index = parent.getChildLayoutPosition(view)
            when (manager) {
                is GridLayoutManager -> {
                    if (manager.orientation == RecyclerView.VERTICAL) {
                        outRect.top = drawable.intrinsicHeight
                        outRect.bottom = drawable.intrinsicHeight
                        outRect.left = drawable.intrinsicWidth
                        outRect.right = drawable.intrinsicWidth
                    } else {
                        outRect.top = drawable.intrinsicHeight
                        outRect.bottom = drawable.intrinsicHeight
                        outRect.left = drawable.intrinsicWidth
                        outRect.right = drawable.intrinsicWidth
                    }
                }
                is LinearLayoutManager -> {
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        outRect.top = drawable.intrinsicHeight
                        outRect.bottom = drawable.intrinsicHeight
                    } else {
                        outRect.left = drawable.intrinsicWidth
                        outRect.right = drawable.intrinsicWidth
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (null == drawable) return

        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        val manager = parent.layoutManager
        if (manager != null) {
            when (manager) {
                is GridLayoutManager -> {
                    val rowCount: Int
                    val columnCount: Int
                    var rowIndex: Int
                    var columnIndex: Int
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        //columnCount = spanCount
                        //rowCount = ceil(parent.childCount.toDouble() / spanCount).toInt()
                        //val x = parent.childCount / spanCount + (if (parent.childCount % spanCount == 0) 0 else 1)

                        for (i in 0 until parent.childCount) {
                            val child: View = parent.getChildAt(i)
                            val lw: Int = manager.getLeftDecorationWidth(child)
                            val th: Int = manager.getTopDecorationHeight(child)
                            val rw: Int = manager.getRightDecorationWidth(child)
                            val bh: Int = manager.getBottomDecorationHeight(child)

//                            val index = parent.getChildAdapterPosition(child)
//                            columnIndex = index % columnCount
//                            rowIndex = index / columnCount
                            if (th > 0) {
                                top = max(child.top - th, parent.paddingTop)
                                top = min(top, parent.height - parent.paddingBottom)
                                bottom = max(child.top, parent.paddingTop)
                                bottom = min(bottom, parent.height - parent.paddingBottom)

                                left = child.left - lw
                                right = child.right + rw
                                if (bottom - top > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 255
                                    drawable.draw(c)
                                }
                            }

                            if (bh > 0) {
                                top = max(child.bottom, parent.paddingTop)
                                top = min(top, parent.height - parent.paddingBottom)
                                bottom = max(child.bottom + bh, parent.paddingTop)
                                bottom = min(bottom, parent.height - parent.paddingBottom)

                                left = child.left - lw
                                right = child.right + rw
                                if (bottom - top > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 200
                                    drawable.draw(c)
                                }
                            }

                            if (lw > 0) {
                                top = max(child.top, parent.paddingTop)
                                top = min(top, parent.height - parent.paddingBottom)
                                bottom = max(child.bottom, parent.paddingTop)
                                bottom = min(bottom, parent.height - parent.paddingBottom)

                                left = child.left - lw
                                right = child.left
                                if (bottom - top > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 150
                                    drawable.draw(c)
                                }
                            }

                            if (rw > 0) {
                                top = max(child.top, parent.paddingTop)
                                top = min(top, parent.height - parent.paddingBottom)
                                bottom = max(child.bottom, parent.paddingTop)
                                bottom = min(bottom, parent.height - parent.paddingBottom)

                                left = child.right
                                right = child.right + rw
                                if (bottom - top > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 100
                                    drawable.draw(c)
                                }
                            }
                        }
                    } else {
                        //rowCount = spanCount
                        //columnCount = parent.childCount / spanCount + (if (parent.childCount % spanCount == 0) 0 else 1)

                        for (i in 0 until parent.childCount) {
                            val child: View = parent.getChildAt(i)
                            val lw: Int = manager.getLeftDecorationWidth(child)
                            val th: Int = manager.getTopDecorationHeight(child)
                            val rw: Int = manager.getRightDecorationWidth(child)
                            val bh: Int = manager.getBottomDecorationHeight(child)

//                            val index = parent.getChildAdapterPosition(child)
//                            columnIndex = index % columnCount
//                            rowIndex = index / columnCount
                            if (th > 0) {
                                top = child.top - th
                                bottom = child.top

                                left = max(child.left - lw, parent.paddingLeft)
                                left = min(left, parent.width - parent.paddingRight)
                                right = max(child.right + rw, parent.paddingRight)
                                right = min(right, parent.width - parent.paddingRight)

                                if (right - left > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 255
                                    drawable.draw(c)
                                }
                            }

                            if (bh > 0) {
                                top = child.bottom
                                bottom = child.bottom + bh

                                left = max(child.left - lw, parent.paddingLeft)
                                left = min(left, parent.width - parent.paddingRight)
                                right = max(child.right + rw, parent.paddingRight)
                                right = min(right, parent.width - parent.paddingRight)
                                if (right - left > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 200
                                    drawable.draw(c)
                                }
                            }

                            if (lw > 0) {
                                top = child.top
                                bottom = child.bottom

                                left = max(child.left - lw, parent.paddingLeft)
                                left = min(left, parent.width - parent.paddingRight)
                                right = max(child.left, parent.paddingRight)
                                right = min(right, parent.width - parent.paddingRight)
                                if (right - left > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 150
                                    drawable.draw(c)
                                }
                            }

                            if (rw > 0) {
                                top = child.top
                                bottom = child.bottom

                                left = max(child.right, parent.paddingLeft)
                                left = min(left, parent.width - parent.paddingRight)
                                right = max(child.right + rw, parent.paddingRight)
                                right = min(right, parent.width - parent.paddingRight)
                                if (right - left > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 100
                                    drawable.draw(c)
                                }
                            }
                        }
                    }
                }
                is LinearLayoutManager -> {
                    if (RecyclerView.VERTICAL == manager.orientation) {
                        left = parent.paddingLeft
                        right = parent.width - parent.paddingRight

                        for (i in 0 until parent.childCount) {
                            //if (i < 1) continue

                            val child: View = parent.getChildAt(i)
                            val lw: Int = manager.getLeftDecorationWidth(child)
                            val th: Int = manager.getTopDecorationHeight(child)
                            val rw: Int = manager.getRightDecorationWidth(child)
                            val bh: Int = manager.getBottomDecorationHeight(child)

                            if (th > 0) {
                                top = max(child.top - th, parent.paddingTop)
                                top = min(top, parent.height - parent.paddingBottom)
                                bottom = max(child.top, parent.paddingTop)
                                bottom = min(bottom, parent.height - parent.paddingBottom)

                                if (bottom - top > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 255
                                    drawable.draw(c)
                                }
                            }

                            if (bh > 0) {
                                top = max(child.bottom, parent.paddingTop)
                                top = min(top, parent.height - parent.paddingBottom)
                                bottom = max(child.bottom + bh, parent.paddingTop)
                                bottom = min(bottom, parent.height - parent.paddingBottom)

                                if (bottom - top > 0) {
                                    drawable.setBounds(left, top, right, bottom)
                                    drawable.alpha = 150
                                    drawable.draw(c)
                                }
                            }
                        }
                    } else {
                        top = parent.paddingTop
                        bottom = parent.height - parent.paddingBottom
                        for (i in 0 until parent.childCount) {
                            val child: View = parent.getChildAt(i)
                            val lw: Int = manager.getLeftDecorationWidth(child)
                            val th: Int = manager.getTopDecorationHeight(child)
                            val rw: Int = manager.getRightDecorationWidth(child)
                            val bh: Int = manager.getBottomDecorationHeight(child)

                            if (lw > 0) {
                                left = max(child.left - lw, parent.paddingLeft)
                                left = min(left, parent.width - parent.paddingRight)
                                right = max(child.left, parent.paddingLeft)
                                right = min(right, parent.width - parent.paddingRight)

                                if (right - left > 0) {
                                    drawable.setBounds(left, child.top, right, child.bottom)
                                    drawable.alpha = 255
                                    drawable.draw(c)
                                }
                            }

                            if (rw > 0) {
                                left = max(child.right, parent.paddingLeft)
                                left = min(left, parent.width - parent.paddingRight)
                                right = max(child.right + lw, parent.paddingLeft)
                                right = min(right, parent.width - parent.paddingRight)

                                if (right - left > 0) {
                                    drawable.setBounds(left, child.top, right, child.bottom)
                                    drawable.alpha = 150
                                    drawable.draw(c)
                                }
                            }
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (null == drawable) return
    }
}