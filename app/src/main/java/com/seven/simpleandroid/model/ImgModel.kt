package com.seven.simpleandroid.model

data class ImgModel(val txt: String, val type: ImgSourceType, val url: String = "", val id: Int = 0)

enum class ImgSourceType {
    Net,
    Disk,
    Heap
}