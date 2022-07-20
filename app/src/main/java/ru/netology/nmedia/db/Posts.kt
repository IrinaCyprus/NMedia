package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.data.Post

//fun Cursor.toPost() = Post(
//    id = getLong(getColumnIndexOrThrow(PostTable.Column.ID.columnName)),
//    authorName = getString(getColumnIndexOrThrow(PostTable.Column.AUTHOR_NAME.columnName)),
//    content = getString(getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)),
//    published = getString(getColumnIndexOrThrow(PostTable.Column.PUBLISHED.columnName)),
//    video = null,
//    like = getInt(getColumnIndexOrThrow(PostTable.Column.LIKE.columnName)),
//    likedByMe = getInt(getColumnIndexOrThrow(PostTable.Column.LIKED_BY_ME.columnName)) != 0,
//    sum_likes = getInt(getColumnIndexOrThrow(PostTable.Column.SUM_LIKES.columnName)),
//    sum_reposts = getInt(getColumnIndexOrThrow(PostTable.Column.SUM_REPOSTS.columnName)),
//    sum_visible = getInt(getColumnIndexOrThrow(PostTable.Column.SUM_VISIBLE.columnName))
//)