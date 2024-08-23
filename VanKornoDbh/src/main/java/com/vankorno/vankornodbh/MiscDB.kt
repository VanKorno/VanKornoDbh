package com.vankorno.vankornodbh

import android.database.sqlite.SQLiteDatabase
import com.vankorno.vankornodbh.ValDB.*

class MiscDB(val db: SQLiteDatabase) {
    
    fun deleteFirstRow(                                                      whichTable: String
    ) {
        val whereClause = "ROWID = (" + select + "ROWID" + from + whichTable + " LIMIT 1)"
        db.delete(whichTable, whereClause, null)
    }
    
    fun buildQuery(                                             whichTable: String,
                                                                    entity: ArrayList<Array<String>>
    ): String {
        var queryStr = dbCreateT + whichTable + dbAutoID
        val last = entity.size - 1
        
        for (ii in 1 until last) {
            queryStr += entity[ii][0] + entity[ii][1] + c
        }
        return queryStr + entity[last][0] + entity[last][1] + ")"
    }
    
    
    
    
    
    
    
    
}