package ru.netology.nmedia.db

import android.os.FileObserver.DELETE
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)  //вставляет новую сущность в таблицу

    @Query("UPDATE posts SET content = :content WHERE id =:id")
    fun update(id: Long, content: String)

    @Query(
        """
        UPDATE posts SET
        sum_likes = sum_likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun like(id: Long)

    @Query("""
        UPDATE posts SET
        sum_reposts = sum_reposts + 1 WHERE id = :id
        """)
    fun share(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Long)

    @Delete
    fun delete(post:PostEntity)

//    @Update
//    fun update(post:PostEntity)

//    @Query
//    fun share(id: Long)
}