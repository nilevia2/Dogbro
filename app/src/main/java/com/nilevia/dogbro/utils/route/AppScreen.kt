package com.nilevia.dogbro.utils.route

enum class ScreenType {
    MAIN_SCREEN, SUB_SCREEN
}

const val ROUTE_LEARN = "learn"
const val ROUTE_QUIZ = "quiz"
const val ROUTE_LEARN_DETAIL = "learnDetail"

sealed class AppScreen(val route: String,val screenType: ScreenType) {
    data object Learn : AppScreen(ROUTE_LEARN, ScreenType.MAIN_SCREEN)
    data object Quiz : AppScreen(ROUTE_QUIZ, ScreenType.MAIN_SCREEN)
    data object LearnDetail: AppScreen(ROUTE_LEARN_DETAIL, ScreenType.SUB_SCREEN)

    companion object{
        fun getScreenByRoute(route: String?): AppScreen? {
            return when (route) {
                ROUTE_LEARN -> Learn
                ROUTE_QUIZ -> Quiz
                ROUTE_LEARN_DETAIL -> LearnDetail
                else -> null
            }
        }
    }
}
