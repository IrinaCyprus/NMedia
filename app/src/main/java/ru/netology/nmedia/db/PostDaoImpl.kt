package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.data.Post

//class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
//
//    override fun getAll() = db.query(
//        PostTable.NAME,
//        PostTable.ALL_COLUMNS_NAMES,
//        null, null, null, null,
//        "${PostTable.Column.ID.columnName} DESC"
//    ).use { cursor ->
//        List(cursor.count) {
//            cursor.moveToNext()
//            cursor.toPost()
//        }
//    }
//
//    override fun save(post: Post): Post {
//        val values = ContentValues().apply {
//            put(PostTable.Column.AUTHOR_NAME.columnName, post.authorName)
//            //TODO
//        }
//        val id = if (post.id != 0L) {
//            db.update(
//                PostTable.NAME, values, "${PostTable.Column.ID.columnName} = ?",
//                arrayOf(post.id.toString())
//            )
//            post.id
//        } else { //post.id =0L
//            db.insert(PostTable.NAME, null, values)
//        }
//        return db.query(
//            PostTable.NAME,
//            PostTable.ALL_COLUMNS_NAMES,
//            "${PostTable.Column.ID.columnName} = ?",
//            arrayOf(id.toString()),
//            null, null, null
//        ).use { cursor ->
//            cursor.moveToNext()
//            cursor.toPost()
//        }
//    }
//
//    override fun likeById(id: Long) {
//        db.execSQL(
//            """
//                UPDATE ${PostTable.NAME} SET
//                sum_likes= sum_likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
//                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
//                WHERE id =?;
//        """.trimIndent(),
//            arrayOf(id)
//        )
//    }
//
//    override fun removeById(id: Long) {
//        db.delete(
//            PostTable.NAME,
//            "${PostTable.Column.ID.columnName}=?",
//            arrayOf(id.toString())
//        )
////        val arg = "0; DROP TABLE *;"
////        db.delete(
////            PostTable.NAME,
////            "${PostTable.Column.ID.columnName}=?",
////            arrayOf(arg)
////        )
//    }
//}