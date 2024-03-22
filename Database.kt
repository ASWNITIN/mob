package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class Database(var context: Context): SQLiteOpenHelper(context, "UserDB", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE Users(Name VARCHAR(50), Age INTEGER)";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(user: Users){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Name", user.name)
        cv.put("Age", user.age)

        var result = db.insert("Users", null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    fun readData(): MutableList<Users>{
        var list: MutableList<Users> = ArrayList()
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM Users", null)
        if(result.moveToFirst()){
            do{
                var user = Users()
                user.name = result.getString(0)
                user.age = result.getString(1).toInt()
                list.add(user)
            }while (result.moveToNext())
        }
        return list
    }

    fun deleteData(name: String) {
        val db = this.writableDatabase
        val result = db.delete("Users", "Name = ?", arrayOf(name))
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(oldName: String, newAge: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("Age", newAge)
        val result = db.update("Users", cv, "Name = ?", arrayOf(oldName))
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show()
        }
    }
}
