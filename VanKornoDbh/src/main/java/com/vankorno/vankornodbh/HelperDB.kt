package com.vankorno.vankornodbh

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.vankorno.vankornodbh.ValDB.*
import com.vankorno.vankornodbh.data.DbTableAndEntt

open class HelperDB(                                                  context: Context,
                                                                      dbName: String,
                                                                   dbVersion: Int,
                                                        private val entities: Array<DbTableAndEntt>,
                                                           val onCreateStart: ()->Unit = {},
                                                          val onCreateFinish: ()->Unit = {},
                                                               val onUpgrade: ()->Unit = {}
) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    private val tag = "HelperDB"
    private val dbLock = Any()
    
    
    override fun onCreate(                                                      db: SQLiteDatabase
    ) {
        // region LOG
        Log.d(tag, "onCreate runs")
        // endregion
        synchronized(dbLock) {
            onCreateStart()
            entities.forEach {
                db.execSQL(MiscDB(db).buildQuery(it.whichTable, it.entity))
            }
            onCreateFinish()
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
            onUpgrade()
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
    
    private inline fun <T> readDB(
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
    private inline fun <T> readWriteDB(
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