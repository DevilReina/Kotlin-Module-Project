data class Archive(
    val name: String,
    val notesMap: MutableMap<Int, Notes>? = mutableMapOf()

)