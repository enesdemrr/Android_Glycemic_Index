package com.project.glisemikindeks.db

import android.content.ContentValues
import android.content.Context
import android.content.ServiceConnection
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import com.project.glisemikindeks.model.CategoryModel
import com.project.glisemikindeks.model.Food
import com.project.glisemikindeks.service.Service

class DBHelper(context:Context,name:String = "food.db", factory: SQLiteDatabase.CursorFactory?=null,version: Int = 1)
    :SQLiteOpenHelper(context,name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE \"Category\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"Name\"\tTEXT UNIQUE,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");")
        db!!.execSQL("CREATE TABLE \"Foods\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"glysemic\"\tTEXT,\n" +
                "\t\"carbohydrate\"\tTEXT,\n" +
                "\t\"cal\"\tTEXT,\n" +
                "\t\"catID\"\tText,\n" +
                "\tPRIMARY KEY(\"ID\"AUTOINCREMENT)\n" +
                ");")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS Category")
        db!!.execSQL("DROP TABLE IF EXISTS Foods")
        onCreate(db)
    }

    fun addFood(Name:String,glysemic:String,carbohydrate:String,cal:String,catID:String) : Long{
        var write = this.writableDatabase
        val values = ContentValues()
        values?.put("Name",Name)
        values?.put("glysemic",glysemic)
        values?.put("carbohydrate",carbohydrate)
        values?.put("cal",cal)
        values?.put("catID",catID)
        val insertCount = write.insert("Foods",null,values)
        return insertCount
    }
    fun addCat(Name: String): Long{
        var write = this.writableDatabase
        val values = ContentValues()
        values.put("Name",Name)
        val insertCount = write.insert("Category",null,values)
        return insertCount
    }
    fun isEmptyFood():Boolean{
        val read = readableDatabase
        val count ="SELECT * FROM Foods"
        val cursor = read.rawQuery(count,null)
        if (cursor!=null)
            if (cursor.count>0){
                return false
            }
        return true
    }
    fun isEmptycat():Boolean{
        val read = readableDatabase
        val count ="SELECT * FROM Category"
        val cursor = read.rawQuery(count,null)
        if (cursor!=null)
            if (cursor.count>0){
                return false
            }
        return true
    }
    fun getAll():ArrayList<Food>{
        var allFood = ArrayList<Food>()
        val read = this.readableDatabase
        val quertFood = "select * from Foods"
        val cursor = read.rawQuery(quertFood,null)
        while (cursor.moveToNext()){
            val ID = cursor.getInt(0)
            val Name = cursor.getString(1)
            val gly = cursor.getString(2)
            val karb = cursor.getString(3)
            val cal = cursor.getString(4)
            val catID = cursor.getInt(5)
            val fooddata = Food(ID,Name,gly,karb,cal,catID)
            allFood.add(fooddata)
        }
        return  allFood
    }
    fun getCatFood(catid: String):ArrayList<Food>{
        var allFood = ArrayList<Food>()
        val read = this.readableDatabase
        val quertFood = "select * from Foods where catID=$catid"
        val cursor = read.rawQuery(quertFood,null)
        while (cursor.moveToNext()){
            val ID = cursor.getInt(0)
            val Name = cursor.getString(1)
            val gly = cursor.getString(2)
            val karb = cursor.getString(3)
            val cal = cursor.getString(4)
            val catID = cursor.getInt(5)
            val fooddata = Food(ID,Name,gly,karb,cal,catID)
            allFood.add(fooddata)
        }
        return  allFood
    }
    fun getCat() :ArrayList<CategoryModel> {
        var ctList = ArrayList<CategoryModel>()
        val read = this.readableDatabase
        val queryCat = "select * from Category"
        val cursor = read.rawQuery(queryCat,null)
        while (cursor.moveToNext()){
            val catID = cursor.getInt(0)
            val catName = cursor.getString(1)
            val ctdata = CategoryModel(catID,catName)
            ctList.add(ctdata)
        }
        return ctList
    }
    fun deleteFood(fID:Int) :Int{
        val write = this.writableDatabase
        val count = write.delete("Foods","ID ="+fID,null)
        return count
    }
    fun deleteFoodCat(cId:String) :Int{
        val write = this.writableDatabase
        val count = write.delete("Foods","catID ="+cId,null)
        return count
    }
    fun deleteCat(cID:Int):Int{
        val write = this.writableDatabase
        val count = write.delete("Category","ID ="+cID, null)
        return count
    }
   fun returnCatId(CName:String): String? {
       val read = this.readableDatabase
       val queryCat = "select * from Category where Name= ?"
       val cursor = read.rawQuery(queryCat, arrayOf(CName)).use {
           if (it.moveToFirst()){
               val catId = it.getInt(0).toString()
               return catId
           }
       }
       return null
   }
    fun updateFood(fID: Int,Name:String,glysemic:String,carbohydrate:String,cal:String,catID:String): Int {
        val write = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Name",Name)
        contentValues.put("glysemic",glysemic)
        contentValues.put("carbohydrate",carbohydrate)
        contentValues.put("cal",cal)
        contentValues.put("catID",catID)
        return write.update("Foods",contentValues,"ID = ?", arrayOf(fID.toString()))

    }
    fun updateCat(cID: Int,Name: String): Int {
        val write = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Name",Name)
        return write.update("Category",contentValues,"ID =?", arrayOf(cID.toString()))
    }
}