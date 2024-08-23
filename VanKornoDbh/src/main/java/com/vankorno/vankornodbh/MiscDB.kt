package com.vankorno.vankornodbh

import android.database.sqlite.SQLiteDatabase
import com.vankorno.vankornodbh.ValDB.from
import com.vankorno.vankornodbh.ValDB.select

class MiscDB(val db: SQLiteDatabase) {
    
    fun deleteFirstRow(                                                      whichTable: String
    ) {
        val whereClause = "ROWID = (" + select + "ROWID" + from + whichTable + " LIMIT 1)"
        db.delete(whichTable, whereClause, null)
    }
    
    
    
    
    
    
    
    
    
    
}