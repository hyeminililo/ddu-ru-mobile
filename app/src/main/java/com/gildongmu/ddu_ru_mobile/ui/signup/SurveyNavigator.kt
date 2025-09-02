package com.gildongmu.ddu_ru_mobile.ui.signup

import com.gildongmu.ddu_ru_mobile.model.signup.survey.SurveyElements

class SurveyNavigator(private val items: List<SurveyElements>) {
    var index: Int = 0
    private set

    val current : SurveyElements get() = items[index]
    val total : Int get() = items.size
    val isLast: Boolean get() = index == items.lastIndex

    fun next() : Boolean{
        if(isLast) return  false
        index++
        return true
    }

    fun prev() : Boolean{
        if(index == 0) return  false
        index--
        return true
    }
    /** 0~100 */
    fun progressPercent(): Int = (index * 100) / total

}
