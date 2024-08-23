package com.vankorno.vankornodbh

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.vankorno.vankornodbh.data.DbTableAndEntt

open class HelperDB(                                             context: Context,
                                                                 dbName: String,
                                                              dbVersion: Int,
                                                   private val entities: Array<DbTableAndEntt>,
                                                      val onCreateStart: (SQLiteDatabase)->Unit = {},
                                                     val onCreateFinish: (SQLiteDatabase)->Unit = {},
                                                          val onUpgrade: (SQLiteDatabase)->Unit = {}
) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    val tag = "HelperDB"
    val dbLock = Any()
    
    
    override fun onCreate(                                                      db: SQLiteDatabase
    ) {
        // region LOG
        Log.d(tag, "onCreate runs")
        // endregion
        synchronized(dbLock) {
            onCreateStart(db)
            
            val miscDB = MiscDB(db)
            entities.forEach {
                db.execSQL(miscDB.buildQuery(it.whichTable, it.entity))
            }
            onCreateFinish(db)
        }
    }
    
    
    override fun onUpgrade(                                                     db: SQLiteDatabase,
                                                                        oldVersion: Int,
                                                                        newVersion: Int
    ) {
        if (oldVersion >= newVersion)  return  //\/\/\/\/\/\
        // region LOG
        Log.d(tag, "onUpgrade runs")
        // endregion
        synchronized(dbLock) {
            onUpgrade(db)
        }
    }
    
    
    
    
    
    
    
    // -------------------------------------------------------------------------------------------
    
    fun writeDB(                                                     logTxt: String,
                                                                        run: (SQLiteDatabase)->Unit
    ) {
        synchronized(dbLock) {
            try {
                writableDatabase.use { run(it) }
            } catch (e: Exception) {
                // region LOG
                Log.e(tag, "Error: $logTxt  $e")
                // endregion
            }
        }
    }
    
    inline fun <T> readDB(
                                                                      logTxt: String,
                                                                defaultValue: T,
                                                                      action: (SQLiteDatabase) -> T
    ): T {
        synchronized(dbLock) {
            return  try {
                        readableDatabase.use { action(it) }
                    } catch (e: Exception) {
                        // region LOG
                        Log.e(tag, "Error: $logTxt  $e")
                        // endregion
                        defaultValue
                    }
        }
    }
    inline fun <T> readWriteDB(
                                                                      logTxt: String,
                                                                defaultValue: T,
                                                                      action: (SQLiteDatabase) -> T
    ): T {
        synchronized(dbLock) {
            return  try {
                        writableDatabase.use { action(it) }
                    } catch (e: Exception) {
                        // region LOG
                        Log.e(tag, "Error: $logTxt  $e")
                        // endregion
                        defaultValue
                    }
        }
    }
    
    
    
    // ============================== GETTERS ===========================================
    
    fun getInt(                                                               whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): Int = readDB("getInt()", 0) { 
        GetSet(it).getInt(whichTable, column, whereClause, whereArg)
    }
    
    
    fun getString(                                                            whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): String = readDB("getString()", "") { 
        GetSet(it).getString(whichTable, column, whereClause, whereArg)
    }
    
    fun getBool(                                                              whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): Boolean = readDB("getBool()", false) { 
        GetSet(it).getBool(whichTable, column, whereClause, whereArg)
    }
    
    fun getLong(                                                              whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): Long = readDB("getLong()", 0L) { 
        GetSet(it).getLong(whichTable, column, whereClause, whereArg)
    }
    
    // ============================== SETTERS ===========================================
    
    fun set(                                                                    value: String,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        writeDB("set()") {
            GetSet(it).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                                     value: Int,
                                                                            whichTable: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ) {
        writeDB("set()") {
            GetSet(it).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                                    value: Boolean,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        writeDB("set()") {
            GetSet(it).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                                    value: Long,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        writeDB("set()") {
            GetSet(it).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    
    
    
    
    
    fun checkIfEmpty(                                                         whichTable: String
    ): Boolean = readDB("checkIfEmpty()", false) { 
        GetSet(it).isEmpty(whichTable)
    }
    
    fun getLastID(                                                            whichTable: String
    ): Int = readDB("getLastID()", 0) { GetSet(it).getLastID(whichTable) }
    
    
    
    fun deleteFirstRow(whichTable: String) = writeDB("deleteFirstRow()") {
        MiscDB(it).deleteFirstRow(whichTable)
    }
    
    
    
    
    
    
}