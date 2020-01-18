package pape.red.fortunecookie

data class ArticleModel(
    var id : Int =0,
    var content : ArrayList<String>? = null,
    var article : Int =0,
    var like : Int =0,
    var question : Boolean = true,
    var title : String ="",
    var comments : ArrayList<CommentModel>? = null
)