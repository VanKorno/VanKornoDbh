package com.vankorno.vankornodbh

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.vankorno.vankornodbh.ValDB.*

open class GetSet(val db: SQLiteDatabase) {
    
    // ============================== GETTERS ===========================================
    
    fun getInt(                                                             whichTable: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): Int {
        val cursor = db.rawQuery(select + column + from + whichTable + where + whereClause+eQ,
                                                                               arrayOf(whereArg) )
        cursor.moveToFirst()
        val mySocks = cursor.getInt(0)
        cursor.close()
        return mySocks
    }
    
    fun getString(                                                          whichTable: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): String {
        val cursor = db.rawQuery(select + column + from + whichTable + where + whereClause+eQ,
                                                                                arrayOf(whereArg) )
        cursor.moveToFirst()
        val mySocks = cursor.getString(0)
        cursor.close()
        return mySocks
    }
    fun getBool(                                                             whichTable: String,
                                                                                 column: String,
                                                                            whereClause: String,
                                                                               whereArg: String
    ): Boolean {
        val cursor = db.rawQuery(select + column + from + whichTable + where + whereClause+eQ
                                                                            ,arrayOf(whereArg) )
        cursor.moveToFirst()
        val mySocks = cursor.getInt(0) == 1
        cursor.close()
        return mySocks
    }
    fun getLong(                                                            whichTable: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): Long {
        val cursor = db.rawQuery(select + column + from + whichTable + where + whereClause+eQ,
                                                                              arrayOf(whereArg) )
        cursor.moveToFirst()
        val mySocks = cursor.getLong(0)
        cursor.close()
        return mySocks
    }
    
    
    // ----------------------------------------------------------------------------------
    
    fun getLastID(                                                            whichTable: String
    ): Int {
        val cursor = db.rawQuery(select + ID + from + whichTable, null)
        
        val mySocks = if (cursor.moveToLast())
                            cursor.getInt(0)
                        else
                            -1
        cursor.close()
        return mySocks
    }
    
    fun getAllIDs(                                                          whichTable: String,
                                                                            orderByStr: String = ""
    ): MutableList<Int> {
        val cursor = db.rawQuery(select + ID + from + whichTable + orderByStr,
                                                                        null)
        val ids = mutableListOf<Int>()
        
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ids
    }
    
    fun isEmpty(                                                              whichTable: String
    ): Boolean {
        val cursor = db.rawQuery(selectAllFrom + whichTable, null)
        val emptiness = cursor.count < 1
        cursor.close()
        return emptiness
    }
    
    fun getNewPriority(                                                       whichTable: String
    ): Int {
        var priority = 1
        val cursor = db.rawQuery(select + Priority + from + whichTable + orderBy + Priority,
                                                                                   null)
        if (cursor.count > 0) {
            cursor.moveToLast()
            priority = cursor.getInt(0) + 1
        }
        cursor.close()
        return priority
    }
    
    
    
    // ============================== SETTERS ===========================================
    
    fun set(                                                                    value: String,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(whichTable, cv, whereClause+eQ, arrayOf(whereArg))
    }
    fun set(                                                                     value: Int,
                                                                            whichTable: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(whichTable, cv, whereClause+eQ, arrayOf(whereArg))
    }
    fun set(                                                                    value: Boolean,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(whichTable, cv, whereClause+eQ, arrayOf(whereArg))
    }
    fun set(                                                                    value: Long,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(whichTable, cv, whereClause+eQ, arrayOf(whereArg))
    }
    
    
    
    
    
    
}