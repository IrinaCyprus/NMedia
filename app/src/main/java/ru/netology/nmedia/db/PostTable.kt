package ru.netology.nmedia.db

//object PostTable {
//
//    const val NAME = "posts"
//
//    val DDL = """
//
//    CREATE TABLE $NAME(
//    ${Column.ID.columnName}INTEGER PRIMARY KEY AUTOINCREMENT,
//    ${Column.AUTHOR_NAME.columnName}TEXT NOT NULL,
//    ${Column.CONTENT.columnName}TEXT NOT NULL,
//    ${Column.PUBLISHED.columnName}TEXT NOT NULL,
//    ${Column.VIDEO.columnName}TEXT,
//    ${Column.LIKE.columnName}INTEGER,
//    ${Column.LIKED_BY_ME.columnName}BOOLEAN NOT NULL DEFAULT 0,
//    ${Column.SUM_LIKES.columnName}INTEGER NOT NULL DEFAULT 0,
//    ${Column.SUM_REPOSTS.columnName}INTEGER NOT NULL DEFAULT 0,
//    ${Column.SUM_VISIBLE.columnName}INTEGER NOT NULL DEFAULT 0
//            );
//    """.trimIndent()
//
//    val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()   //массив строк в котором все колонки
//
//    enum class Column(val columnName: String) {
//        ID("id"),
//        AUTHOR_NAME("author"),
//        CONTENT("content"),
//        PUBLISHED(" published"),
//        VIDEO("video"),
//        LIKE("like"),
//        LIKED_BY_ME("likedByMe"),
//        SUM_LIKES("likes"),
//        SUM_REPOSTS("reposts"),
//        SUM_VISIBLE("visible")
//    }
//}