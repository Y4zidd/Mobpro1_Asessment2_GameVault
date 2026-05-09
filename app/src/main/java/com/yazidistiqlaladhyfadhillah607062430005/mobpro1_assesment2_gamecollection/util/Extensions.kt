package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return format.format(date)
}
