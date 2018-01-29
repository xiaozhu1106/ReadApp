package com.example.zzbmi.readapp.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination


/**
 * @author ZhuZiBo
 * @date 2017/12/8
 */
object PinYinUtils {
    private var format: HanyuPinyinOutputFormat? = null

    /**
     * 拼音全称
     */
    fun hanZiToFullPinyin(hanZi: String): String {
        val chars = hanZi.toCharArray()
        if (format == null) {
            //设置输出配置
            format = HanyuPinyinOutputFormat()
        }
        //设置大写
        format!!.caseType = HanyuPinyinCaseType.UPPERCASE
        //取消声调
        format!!.toneType = HanyuPinyinToneType.WITHOUT_TONE
        val sb = StringBuilder()
        chars.filterNot { Character.isWhitespace(it) }
                .forEach {
                    if (Character.toString(it).matches("[\\u4E00-\\u9FA5]".toRegex())) {
                        //汉字
                        try {
                            val letter = PinyinHelper.toHanyuPinyinStringArray(it, format)[0]
                            sb.append(letter)
                        } catch (badHanyuPinyinOutputFormatCombination: BadHanyuPinyinOutputFormatCombination) {
                            badHanyuPinyinOutputFormatCombination.printStackTrace()
                        }

                    } else {
                        if (Character.isLetter(it)) {//字母
                            sb.append(Character.toUpperCase(it))
                        } else {//乱七八糟的不认识的字符%$^$
                            sb.append("#")
                        }
                    }
                }
//        Log.i("test", sb.toString())
        return sb.toString()
    }


    /**
     * 拼音首字母缩写
     */
    fun hanZiToShortPinyin(hanZi: String): String {
        val chars = hanZi.toCharArray()
        if (format == null) {
            //设置输出配置
            format = HanyuPinyinOutputFormat()
        }
        //设置大写
        format!!.caseType = HanyuPinyinCaseType.UPPERCASE
        //取消声调
        format!!.toneType = HanyuPinyinToneType.WITHOUT_TONE
        val sb = StringBuilder()
        chars.filterNot { Character.isWhitespace(it) }
                .forEach {
                    if (Character.toString(it).matches("[\\u4E00-\\u9FA5]".toRegex())) {
                        //汉字
                        try {
                            val letter = PinyinHelper.toHanyuPinyinStringArray(it, format)[0]
                            sb.append(letter[0])
                        } catch (badHanyuPinyinOutputFormatCombination: BadHanyuPinyinOutputFormatCombination) {
                            badHanyuPinyinOutputFormatCombination.printStackTrace()
                        }

                    } else {
                        if (Character.isLetter(it)) {//字母
                            sb.append(Character.toUpperCase(it))
                        } else {//乱七八糟的不认识的字符%$^$
                            sb.append("#")
                        }
                    }
                }
//        Log.i("test", sb.toString())
        return sb.toString()
    }
}