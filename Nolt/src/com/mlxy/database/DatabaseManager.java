package com.mlxy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/** 
 * �ʼ����ݿ��������
 * */
public class DatabaseManager extends SQLiteOpenHelper {
	/** ���ݿ�����*/
	public static final String DB_NAME = "memo.db";
	/** ���ݿ�汾��*/
	public static final int DB_VERSION = 1;
	
	/** ��űʼ����ݵı�����*/
	public static final String TABLE_NAME_MEMO = "memos";
	
	/** �ʼ���Ŀ��ID������
	 * ������integer(int)��*/
	public static final String COLUMN_NAME_MEMO_ID = "id";
	
	/** �ʼ���Ŀ�ı���������
	 * ������text��*/
	public static final String COLUMN_NAME_MEMO_TITLE = "title";
	
	/** �ʼ���Ŀ������������
	 * ������text��*/
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
		// ������
		String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
									TABLE_NAME_MEMO,
									COLUMN_NAME_MEMO_ID,
									COLUMN_NAME_MEMO_TITLE,
									COLUMN_NAME_MEMO_CONTENT);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: ���ݿ�汾������
	}
	
}
