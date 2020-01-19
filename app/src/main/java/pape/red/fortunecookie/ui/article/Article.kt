package pape.red.fortunecookie.ui.article

data class Article(
    val type: Int, // 0 = 글 1 = 사진 2 = 댓글 아 귀찮아 롤하고싶다 3 = 하트
    val content: String
)