package com.birdv5.ir.database;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;



public class ArticlesTable extends SQLTable {

    private static final String NAME = "articles";

    public static final class Columns {
    	public static final String COLUMN_CONTENT = "content";
    	public static final String COLUMN_TYPE = "type";
    	public static final String COLUMN_ID = "id";
    }
    
    private static final class Holder {
        public static final ArticlesTable INSTANCE = new ArticlesTable();
    }

    public static synchronized ArticlesTable getInstance() {
        return Holder.INSTANCE;
    }

    private ArticlesTable() {}
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected String getUniqueConstraint() {
    	 return "";
    }

    public static ContentValues getContentValues(String title,String content,int type) {
        ContentValues values = new ContentValues();
        values.put(Columns.COLUMN_CONTENT, content);
        values.put(Columns.COLUMN_TYPE, type);
        return values;
    }
    
   
    @Override
    protected Map<String, String> getColumnMapping() {
        final Map<String, String> map = new LinkedHashMap<String, String>();
        map.put(BaseColumns._ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
        map.put(Columns.COLUMN_CONTENT, "TEXT NOT NULL");
        map.put(Columns.COLUMN_TYPE, "INTEGER NOT NULL");
        return map;
    }
    
   
    
    public int queryCount(final SQLiteDatabase database,final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        return super.query(database,projection,selection, selectionArgs,sortOrder).getCount();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }
}
