package com.example.ddu_ru_mobile.model.signup.api

//Todo String으로 보내는게 적절한지 Enum으로 보내는게 적절한지 생각해보는게 나을 듯
data class TravelPreferenceRequest(val nickName: String, val preferencesList: MutableList<String>?)
