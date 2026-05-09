package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main

sealed interface MainAction {
    data class OnLayoutToggle(val isList: Boolean) : MainAction
    data class OnDeleteGame(val id: Long) : MainAction
    data class OnThemeChange(val mode: Int) : MainAction
}
