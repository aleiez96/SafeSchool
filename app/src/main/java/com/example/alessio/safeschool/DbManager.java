package com.example.alessio.safeschool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DbManager
{
	private DataBaseHelper dbhelper;


	public DbManager(Context ctx)
	{
		this.dbhelper=new DataBaseHelper(ctx);
	}

	public long insert(String tableName, ContentValues values)
	{
		SQLiteDatabase db = this.dbhelper.getWritableDatabase();
		try
		{
			long rowId = db.insert(tableName, null, values);
			return rowId;
		}
		catch (SQLiteException sqle)
		{
			// Gestione delle eccezioni
            return  -1;
		}
	}

	public int delete(String tableName, String whereClause, String[] whereArgs)
	{
		SQLiteDatabase db = this.dbhelper.getWritableDatabase();
		try
		{
			return db.delete(tableName, whereClause, whereArgs);
		}
		catch (SQLiteException sqle)
		{
            // Gestione delle eccezioni
			return -1;
		}

	}

	public Cursor query(String tableName, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit)
	{
		Cursor crs = null;
		try
		{
			SQLiteDatabase db=dbhelper.getReadableDatabase();
			crs=db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		}
		catch(SQLiteException sqle)
		{
            // Gestione delle eccezioni
			return null;
		}
		return crs;
	}

	public Cursor query(String sql, String[] selectionArgs){
		SQLiteDatabase db=dbhelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, selectionArgs);
		}
		catch(SQLiteException sqle)
		{
			// Gestione delle eccezioni
			return null;
		}
		return cursor;
	}

}
