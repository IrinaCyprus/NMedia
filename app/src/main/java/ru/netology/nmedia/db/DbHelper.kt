package ru.netology.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.deleteDatabase
import android.database.sqlite.SQLiteOpenHelper

// вспомогательный класс, позволяет создавать базу при первом запуске и
// обновлять при изменении версии(когда пользователь обновляет приложение)
class DbHelper(
    context: Context,
    dbVersion: Int,
    dbName: String,
    private val DDLs: Array<String>
) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        DDLs.forEach(db::execSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        if (oldVersion == 1 && newVesion == 2) {
//            moveOldDataToNewStructure();
//            deleteOldDataBase();
//            deleteDatabase()
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        super.onDowngrade(db, oldVersion, newVersion)
    }
}

