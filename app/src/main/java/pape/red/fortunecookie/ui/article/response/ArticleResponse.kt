package pape.red.fortunecookie.ui.article.response

data class ArticleResponse(
    val title: String,
    val tags: ArrayList<String>,
    val content: ArrayList<String>,
    val author: String,
    val question: Boolean,
    val comments: ArrayList<CommentResponse>
)