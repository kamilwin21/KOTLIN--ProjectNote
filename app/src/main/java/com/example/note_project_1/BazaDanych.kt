package com.example.note_project_1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

//Obiekt przechowuje nazwę tabeli oraz nazwy kolumn tabeli
object FeedEntry: BaseColumns{
    const val TABLE_NAME = "entry"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_MESSAGE = "message"

}

//Obiekt posiada zmienne, które umożliwiają tworzenie oraz usuwanie tablicy
object BasicCommand{
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} ("+
                "${BaseColumns._ID} INTEGER PRIMARY KEY,"+
                "${FeedEntry.COLUMN_NAME_TITLE} TEXT NOT NULL,"+
                "${FeedEntry.COLUMN_NAME_MESSAGE} TEXT NOT NULL)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"



}
class BazaDanych(context: Context): SQLiteOpenHelper(context, FeedEntry.TABLE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Tworzenie tabeli
        db?.execSQL(BasicCommand.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Usuwanie tabeli po wprowadzeniu zmian oraz następne jej tworzenie
        db?.execSQL(BasicCommand.SQL_DELETE_ENTRIES)
        onCreate(db)
    }


}