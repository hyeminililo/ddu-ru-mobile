package com.gildongmu.ddu_ru_mobile.model.signup.api

//Todo String으로 보내는게 적절한지 Enum으로 보내는게 적절한지 생각해보는게 나을 듯 -> 아마 string이 나을 것같은데 //preferencesList가 아닌 answer로 바꾸기
data class TravelPreferenceRequest(val surveyVersion: Int, val nickName: String, val preferencesList: MutableList<String>?)
