package com.example.ddu_ru_mobile.model.signup.survey

class SurveyList {
    val surveyList = listOf(
        Survey(
            surveyId = 1,
            question = "앗싸 여행이다!!  그런데 여획은 어쩌지…?",
            options = listOf("📝경로, 맛집, 시간까지 완벽하게!", " 무계획이 주는 즐거움이 있지~")
        ),
        Survey(
            surveyId = 2,
            question = "유명한 맛집이 대기가 2시간ㅜㅜ",
            options = listOf("이왕이면 기다려서라도 먹어야해 !", "기다리기는 좀... 편하게 주변에서 먹자")
        ),
        Survey(
            surveyId = 3,
            question = "여행 준비 완료! 그런데 숙소는…",
            options = listOf("잠은 갖춰진 곳에서 자야지", "잠만 잘 수 있으면 OK!")
        ),
        Survey(
            surveyId = 4,
            question = "우리 경비는 어떻게 할까?",
            options = listOf("꼼꼼하게 각자 결제!", "한 통장에 모아 함께 쓰기!")
        ),
        Survey(
            surveyId = 5,
            question = "다음 여행지로 출발~ 어떻게 가지?",
            options = listOf("여행은 낭만이지! 걷거나 버스", "편한게 최고~ 택시 타는 거 어때?")
        ),
        Survey(
            surveyId = 6,
            question = "여행지에서 지갑을 여는 순간! \uD83D\uDCB8 나는…",
            options = listOf("여행갔으면 써야지! vs ", "아무래도 가성비가 최고지~")
        ),
        Survey(
            surveyId = 7,
            question = "우와… 여기 너무 이쁘다!",
            options = listOf("남는 건 사진 뿐이야!", "풍경은 눈에 담고 싶어")
        ),
        Survey(
            surveyId = 8,
            question = "여행지에서의 하루! 어떻게 보내는 게 좋을까?",
            options = listOf("일찍 일어나서 알차게 돌아다니자!", "서두르기보단 느긋하게 돌아다니자~")
        ),
        Survey(
            surveyId = 9,
            question = "여행 가서 주로 뭐 하고 싶어? (최대 3개)",
            options = listOf("관광", "관람","자연 탐방","먹방","쇼핑","휴양","액티비티","놀이공원","페스티벌")
        ),
    )
}