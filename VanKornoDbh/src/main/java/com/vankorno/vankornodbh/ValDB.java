package com.vankorno.vankornodbh;

public class ValDB {
    public static final String
InMemoryDB=":memory:",
select="SELECT ", from=" FROM ", selectAllFrom="SELECT * FROM ",
deleteFrom="DELETE FROM ",
where=" WHERE ",    equal="=",    eQ="=?",          less="<",       more=">",
                       ne="!=",  neQ="!=?",        lessQ="<?",     moreQ=">?",
                                                      le="<=",        me=">=",
                                                     leQ="<=?",      meQ=">=?",
c=", ", and=" AND ", or=" OR ",
orderBy=" ORDER BY ", like=" LIKE ",
dbInt=" INT", dbStr=" TEXT NOT NULL",
dbBool=" BOOL", dbLong=" BIGINT", dbFloat=" REAL", dbBlob=" BLOB",
ID="ID", descending=" DESC",
dbAutoID = " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ",
dbCreateT="CREATE TABLE ",
dbDrop="DROP TABLE IF EXISTS ",

    // TABLES
TTTMisc="TTTMisc", TTTUndo="TTTUndo", TTTStats="TTTStats",

    // COLUMNS
Bool1="Bool1",  Long1="Long1",   Float1="Float1",
Int1="Int1",    Int2="Int2",
Str1="Str1",    Str2="Str2",
    
Name="Name", NameUI="NameUI", TableName="TableName", TableNumber="TableNumber",
Text="Text", Priority="Priority", Active="Active"
;
}