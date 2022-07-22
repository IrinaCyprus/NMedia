package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.data.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {

    override fun getAll() = db.query(
        PostTable.NAME,
        PostTable.ALL_COLUMNS_NAMES,
        null,
        null,
        null,
        null,
        "${PostTable.Column.ID.columnName} DESC"
    ).use { cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostTable.Column.AUTHOR_NAME.columnName, post.authorName)    //ContentValues специальный тип, позволяющий задавать значения
            put(PostTable.Column.CONTENT.columnName, post.content)            //которые потом будут использоваться в insert/update
            put(PostTable.Column.PUBLISHED.columnName, post.published)
            put(PostTable.Column.VIDEO.columnName, post.video)
//            put(PostTable.Column.LIKE.columnName, post.like)
//            put(PostTable.Column.LIKED_BY_ME.columnName, post.likedByMe)
//            put(PostTable.Column.SUM_LIKES.columnName, post.sum_likes)
//            put(PostTable.Column.SUM_REPOSTS.columnName,post.sum_reposts)
//            put(PostTable.Column.SUM_VISIBLE.columnName,post.sum_visible)
        }
        val id = if (post.id != 0L) {
            db.update(
                PostTable.NAME, values, "${PostTable.Column.ID.columnName} = ?", //? -placeholder, на место которого будет подставляться конкретное значение(используется при любых запросах, чтобы не получить SQL Injection
                arrayOf(post.id.toString())
            )
            post.id
        } else { //post.id =0L
            db.insert(PostTable.NAME, null, values)
        }
        return db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS_NAMES,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()),
            null, null, null
        ).use { cursor ->
            cursor.moveToNext()  // перемещает курсор на следующую позицию и возвращает true, если там есть данные или false если данных нет
            cursor.toPost()
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostTable.NAME} SET
                sum_likes = sum_likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id =?;
        """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.ID.columnName}=?",
            arrayOf(id.toString())
        )
    }

        override fun share(id: Long) {
            db.execSQL(
                """
                    UPDATE ${PostTable.NAME} SET
                    sum_repost = sum_repost +1
                    WHERE id =?;
                  """.trimIndent(),
            arrayOf(id)
            )
        }

// cursor спецаильный обьект реализующий интерфейс Closeable, который позволяет перемещаться по выбраным строкам


//        val arg = "0; DROP TABLE *;"
//        db.delete(
//            PostTable.NAME,
//            "${PostTable.Column.ID.columnName}=?",
//            arrayOf(arg)
//        )

}