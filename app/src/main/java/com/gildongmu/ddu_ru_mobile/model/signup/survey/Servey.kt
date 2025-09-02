package com.gildongmu.ddu_ru_mobile.model.signup.survey

class Servey {
    var planStyle: PlanStyle? = null
    var tastingStyle : TastingStyle? = null
    var stayStyle : StayStyle? = null
    var expenseStyle : ExpenseStyle? = null
    var moveStyle :  MoveStyle? = null
    var spendStyle : SpendStyle? = null
    var captureStyle : CaptureStyle? = null
    var paceStyle : PaceStyle? = null
    var interests :  Set<Interest>? = null

    // Todo Pair로 해서 두개 중 하나 선택해서 넣는게 어떨지 ,,
    enum class PlanStyle { PLANNER, FREE }

    enum class TastingStyle { WAIT, NEARBY }

    enum class StayStyle { HOTEL, JUST_SLEEP }

    enum class ExpenseStyle { EACH_PAYS, POOLED }

    enum class MoveStyle { WALK_BUS, TAXI }

    enum class SpendStyle { SPLURGE, SAVER }

    enum class CaptureStyle { PHOTO, EYES }

    enum class PaceStyle { EARLY_FULL, RELAXED }

    enum class Interest {
        SIGHTSEEING, EXHIBITION, NATURE, FOOD, SHOPPING, RESORT,
        ACTIVITY, THEME_PARK, FESTIVAL
    }

    fun toServerMap(): Map<String, String?> {
        return mapOf(
            "planStyle" to planStyle?.name,
            "tastingStyle" to tastingStyle?.name,
            "stayStyle" to stayStyle?.name,
            "expenseStyle" to expenseStyle?.name,
            "moveStyle" to moveStyle?.name,
            "spendStyle" to spendStyle?.name,
            "captureStyle" to captureStyle?.name,
            "paceStyle" to paceStyle?.name,
            "interests" to interests?.joinToString(",") { it.name }
        )
    }
    fun toServerValuse(): String {
        return this.toString()
    }
}
