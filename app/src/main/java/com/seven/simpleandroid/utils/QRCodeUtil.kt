package com.seven.simpleandroid.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.support.annotation.ColorInt
import android.text.TextUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

/**
 * https://www.jianshu.com/p/c75f16de1b2c
 */
class QRCodeUtil {

    companion object {
        /*
        fun createQRCode(str: String) : Bitmap? {
            return createQRCode(str, 200)
        }

        fun createQRCode(str: String, size : Int): Bitmap? {
            val multiFormatWriter = MultiFormatWriter()
            var bitmap : Bitmap? = null
            try {
                val matrix = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, size, size)
                val mWidth = matrix.width
                val mHeight = matrix.height
                val pixels = IntArray(mWidth * mHeight)
                for (y in 0 until mHeight) {
                    val offset = y * mWidth
                    for (x in 0 until mWidth) {
                        pixels[offset + x] = if (matrix.get(x, y)) -0x1000000 else -0x1 //黑色和白色
                    }
                }
                bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
                bitmap.setPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight)

            } catch (e: WriterException) {
                e.printStackTrace()
            }

            return bitmap
        }
        */

        /**
         * 创建二维码位图
         *
         * @param content 字符串内容
         * @param size 位图宽&高(单位:px)
         * @return
         */
        fun createQRCodeBitmap(content: String?, size : Int) : Bitmap? {
            return createQRCodeBitmap(content, size, "UTF-8", "H", "4", Color.BLACK, Color.WHITE, null, null, 0F);
        }

        /**
         * 创建二维码位图 (自定义黑、白色块颜色)
         *
         * @param content 字符串内容
         * @param size 位图宽&高(单位:px)
         * @param color_black 黑色色块的自定义颜色值
         * @param color_white 白色色块的自定义颜色值
         * @return
         */
        fun createQRCodeBitmap(content : String?, size : Int, @ColorInt color_black : Int, @ColorInt color_white : Int) : Bitmap? {
            return createQRCodeBitmap(content, size, "UTF-8", "H", "4", color_black, color_white, null, null, 0F);
        }

        /**
         * 创建二维码位图 (带Logo小图片)
         *
         * @param content 字符串内容
         * @param size 位图宽&高(单位:px)
         * @param logoBitmap logo图片
         * @param logoPercent logo小图片在二维码图片中的占比大小,范围[0F,1F]。超出范围->默认使用0.2F
         * @return
         */
        fun createQRCodeBitmap(content : String, size : Int, logoBitmap : Bitmap?, logoPercent : Float) : Bitmap? {
            return createQRCodeBitmap(content, size, "UTF-8", "H", "4", Color.BLACK, Color.WHITE, null, logoBitmap, logoPercent);
        }

        /**
         * 创建二维码位图 (Bitmap颜色代替黑色) 注意!!!注意!!!注意!!! 选用的Bitmap图片一定不能有白色色块,否则会识别不出来!!!
         *
         * @param content 字符串内容
         * @param size 位图宽&高(单位:px)
         * @param targetBitmap 目标图片 (如果targetBitmap != null, 黑色色块将会被该图片像素色值替代)
         * @return
         */
        fun createQRCodeBitmap(content : String, size : Int, targetBitmap : Bitmap ) : Bitmap? {
            return createQRCodeBitmap(content, size, "UTF-8", "H", "4", Color.BLACK, Color.WHITE, targetBitmap, null, 0F);
        }

        /**
         * 创建二维码位图 (支持自定义配置和自定义样式)
         *
         * @param content 字符串内容
         * @param size 位图宽&高(单位:px)
         * @param character_set 字符集/字符转码格式 (支持格式:{@link CharacterSetECI })。传null时,zxing源码默认使用 "ISO-8859-1"
         * @param error_correction 容错级别 (支持级别:{@link ErrorCorrectionLevel })。传null时,zxing源码默认使用 "L"
         * @param margin 空白边距 (可修改,要求:整型且>=0), 传null时,zxing源码默认使用"4"。
         * @param color_black 黑色色块的自定义颜色值
         * @param color_white 白色色块的自定义颜色值
         * @param targetBitmap 目标图片 (如果targetBitmap != null, 黑色色块将会被该图片像素色值替代)
         * @param logoBitmap logo小图片
         * @param logoPercent logo小图片在二维码图片中的占比大小,范围[0F,1F],超出范围->默认使用0.2F。
         * @return
         */
        fun createQRCodeBitmap(content : String?, size : Int, character_set : String?, error_correction : String?, margin : String?, @ColorInt color_black : Int, @ColorInt color_white : Int, targetBitmap : Bitmap?, logoBitmap : Bitmap?, logoPercent : Float) : Bitmap? {

            /** 1.参数合法性判断 */
            if(TextUtils.isEmpty(content)){ // 字符串内容判空
                return null
            }

            if(size <= 0){ // 宽&高都需要>0
                return null
            }

            try {
                /** 2.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
                //Hashtable<EncodeHintType, String> hints = new Hashtable<>();
                val hints = hashMapOf<EncodeHintType, String>()

                if(!character_set.isNullOrEmpty()) {
                    // 字符转码格式设置
                    hints[EncodeHintType.CHARACTER_SET] = character_set!!
                }

                if(!error_correction.isNullOrEmpty()){
                    // 容错级别设置
                    hints[EncodeHintType.ERROR_CORRECTION] = error_correction!!
                }

                if(!margin.isNullOrEmpty()){
                    // 空白边距设置
                    hints[EncodeHintType.MARGIN] = margin!!
                }
                val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

                /** 3.根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
                var mTargetBitmap = targetBitmap
                if(mTargetBitmap != null){
                    mTargetBitmap = Bitmap.createScaledBitmap(mTargetBitmap, size, size, false)
                }

                val pixels = IntArray(size * size)
                for(y in 0 until size){
                    for(x in 0 until size){
                    if(bitMatrix.get(x, y)){ // 黑色色块像素设置
                        if(targetBitmap != null) {
                            pixels[y * size + x] = mTargetBitmap!!.getPixel(x, y)
                        } else {
                            pixels[y * size + x] = color_black
                        }
                    } else { // 白色色块像素设置
                        pixels[y * size + x] = color_white
                    }
                }
                }

                /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象 */
                val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

                /** 5.为二维码添加logo小图标 */
                if(logoBitmap != null){
                    return addLogo(bitmap, logoBitmap, logoPercent);
                }

                return bitmap
            } catch (e : WriterException) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * 向一张图片中间添加logo小图片(图片合成)
         *
         * @param srcBitmap 原图片
         * @param logoBitmap logo图片
         * @param logoPercent 百分比 (用于调整logo图片在原图片中的显示大小, 取值范围[0,1], 传值不合法时使用0.2F)原图片是二维码时,建议使用0.2F,百分比过大可能导致二维码扫描失败。
         * @return
         */
        fun addLogo(srcBitmap : Bitmap, logoBitmap : Bitmap?, logoPercent : Float = 0.2f) : Bitmap? {

            if(logoBitmap == null){
                return srcBitmap
            }

            var mPercent = logoPercent
            if(mPercent < 0F || mPercent > 1F) {
                mPercent = 0.2F
            }

            /** 2. 获取原图片和Logo图片各自的宽、高值 */
            val srcWidth = srcBitmap.width
            val srcHeight = srcBitmap.height
            val logoWidth = logoBitmap.width
            val logoHeight = logoBitmap.height

            /** 3. 计算画布缩放的宽高比 */
            val scaleWidth = srcWidth * mPercent / logoWidth
            val scaleHeight = srcHeight * mPercent / logoHeight

            /** 4. 使用Canvas绘制,合成图片 */
            val bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawBitmap(srcBitmap, 0f, 0f, null)
            canvas.scale(scaleWidth, scaleHeight, srcWidth/2f, srcHeight/2f)
            canvas.drawBitmap(logoBitmap, srcWidth/2f - logoWidth/2f, srcHeight/2f - logoHeight/2f, null)

            return bitmap
        }

        /**
         * 生成条形码
         * @param contents      条形码内容
         * @param desiredWidth  宽度
         * @param desiredHeight 高度
         * @return                  bitmap
         * @throws WriterException  异常
         */
        @Throws(WriterException::class)
        fun createBarcode(contents: String, desiredWidth: Int, desiredHeight: Int): Bitmap {
            val writer = MultiFormatWriter()
            val result =
                writer.encode(contents, BarcodeFormat.CODE_128, desiredWidth, desiredHeight)

            val width = result.width
            val height = result.height
            val pixels = IntArray(width * height)
            // All are 0, or black, by default
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (result.get(x, y)) Color.BLACK else Color.WHITE
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }
    }
}