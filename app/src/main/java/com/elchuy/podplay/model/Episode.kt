package com.elchuy.podplay.model

import java.util.*

data class Episode(
    var guid: String = "",
    var title: String = "",
    var description: String = "",
    var mediaUrl: String = "",
    var releaseData: Date = Date(),
    var duration: String
)