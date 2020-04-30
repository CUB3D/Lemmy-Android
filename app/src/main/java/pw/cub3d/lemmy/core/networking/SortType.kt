package pw.cub3d.lemmy.core.networking

enum class SortType(val id: String) {
    HOT("Hot"),
    NEW("New"),
    TOP_DAY("TopDay"),
    TOP_WEEK("TopWeek"),
    TOP_MONTH("TopMonth"),
    TOP_YEAR("TopYear"),
    TOP_ALL("TopAll")
}