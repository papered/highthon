package pape.red.fortunecookie.ui.write

import androidx.databinding.ObservableField

data class Content(
    val imageUrl: String = "",
    val isImage: Boolean = false,
    val content: ObservableField<String> = ObservableField()
)