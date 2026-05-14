package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main

sealed interface MainAction {
    data class OnLayoutToggle(val isList: Boolean) : MainAction
    data class OnDeleteGame(val id: Long) : MainAction
    data class OnRestoreGame(val id: Long) : MainAction
    data class OnDeletePermanently(val id: Long) : MainAction
    data class OnThemeChange(val mode: Int) : MainAction
    data class OnColorChange(val color: String) : MainAction
}
