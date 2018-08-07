package com.seven.simpleandroid.model

data class ImgModel(val txt: String, val type: ImgSourceType, val url: String = "", val id: Int = 0)

enum class ImgSourceType(val value: Int) {
    Net(0),
    Disk(1),
    Heap(2)
}