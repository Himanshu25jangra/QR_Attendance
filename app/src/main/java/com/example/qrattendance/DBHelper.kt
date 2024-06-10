package com.example.qrattendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper : SQLiteOpenHelper {
    lateinit var context: Context

    companion object {
        val DATABASE_NAME = "UserCredentials.db"
        val DATABASE_VERSION = 1

        val TABLE_NAME = "User_Information"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "User_Name"
        val COLUMN_EMAIL = "User_Email"
        val COLUMN_PASSWORD = "User_Password"
    }

    constructor(context: Context) : super(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {  //checks if DATABASE EXISTS
        this.context = context
    }

    override fun onCreate(db: SQLiteDatabase?) { //This method is called when the database is created for the first time
        //The below (val query) defines an SQL query string that creates a table.
        // It uses string interpolation to insert table and column names into the query
        val query =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_EMAIL TEXT, $COLUMN_PASSWORD TEXT);"
        db?.execSQL(query)//This line executes the SQL query (query) using the execSQL method of the SQLiteDatabase object (db).
        //The ?. operator is used to safely call execSQL only if db is not null.
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun registerUser(name: String, email: String, password: String) {
        val db: SQLiteDatabase = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_EMAIL, email)
        contentValues.put(COLUMN_PASSWORD, password)

        val result: Long = db.insert(TABLE_NAME, null, contentValues)

        if (result.toInt() == -1) {
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Registration Successfully Done!", Toast.LENGTH_SHORT).show()
        }
        db.close()  //Remember to always call db.close() when you're done using the database instance.
    }


    //CHECK USER LOGIN
    var loggedin: Boolean = false
    fun loginUser(email: String, password: String): Boolean {

        val db: SQLiteDatabase = this.readableDatabase
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = '$email' AND $COLUMN_PASSWORD = '$password'"

        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            loggedin = true
        } else {
            loggedin = false
        }
        return loggedin
    }

}