package com.mlxy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/** 
 * 笔记数据库管理器。
 * */
public class DatabaseManager extends SQLiteOpenHelper {
	/** 数据库名。*/
	public static final String DB_NAME = "memo.db";
	/** 数据库版本。*/
	public static final int DB_VERSION = 1;
	
	/** 存放笔记数据的表名。*/
	public static final String TABLE_NAME_MEMO = "memos";
	
	/** 笔记项目的ID列名。
	 * 类型是integer(int)。*/
	public static final String COLUMN_NAME_MEMO_ID = "id";
	
	/** 笔记项目的标题列名。
	 * 类型是text。*/
	public static final String COLUMN_NAME_MEMO_TITLE = "title";
	
	/** 笔记项目的内容列名。
	 * 类型是text。*/
	public static final String COLUMN_NAME_MEMO_CONTENT = "content";
	
	public DatabaseManager(Context context) {
		this(context, DB_NAME, null, DB_VERSION);
	}
	
	public DatabaseManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表。
		String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
									TABLE_NAME_MEMO,
									COLUMN_NAME_MEMO_ID,
									COLUMN_NAME_MEMO_TITLE,
									COLUMN_NAME_MEMO_CONTENT);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: 数据库版本升级。
	}
	
}
