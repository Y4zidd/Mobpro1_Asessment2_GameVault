package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.detail

sealed interface DetailAction {
    data class OnTitleChange(val title: String) : DetailAction
    data class OnPlatformChange(val platform: String) : DetailAction
    data class OnRatingChange(val rating: Float) : DetailAction
    data class OnPlayTimeChange(val playTime: String) : DetailAction
    data class OnFinishedChange(val finished: Boolean) : DetailAction
    data class OnImageUrlChange(val url: String) : DetailAction
    data object OnSave : DetailAction
    data object OnDelete : DetailAction
}
