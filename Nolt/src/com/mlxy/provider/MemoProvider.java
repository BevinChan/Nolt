package com.mlxy.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mlxy.database.DatabaseManager;

/** 笔记数据的提供器。*/
public class MemoProvider extends ContentProvider {
	/** 笔记信息的匹配码。*/
	public static final int CODE_MEMOS = 233;
	
	private DatabaseManager manager;
	
	/** 匹配传来的URI。*/
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		matcher.addURI("memos", null, CODE_MEMOS);
	}
	
	public MemoProvider() {
	}

	@Override
	public boolean onCreate() {
		// 初始化数据库管理器。
		manager = new DatabaseManager(getContext());
		return false;
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// 准备要返回的游标。
		Cursor cursor = null;
		
		// 对传入的URI进行匹配并分别处理。
		int code = matcher.match(uri);
		
		switch (code) {
		case CODE_MEMOS:
			// 打开数据库。
			SQLiteDatabase database = manager.getReadableDatabase();
			
			// 执行查询。
			cursor = database.query(DatabaseManager.TABLE_NAME_MEMO, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		
		return cursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// 对传入的URI进行匹配并分别处理。
		int code = matcher.match(uri);
		
		switch (code) {
		case CODE_MEMOS:
			// 打开数据库。
			SQLiteDatabase db = manager.getWritableDatabase();
			
			// 插入。
			db.insert(DatabaseManager.TABLE_NAME_MEMO, null, values);
			break;
		}
		return uri;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int rows = 0;
		
		// 对传入的URI进行匹配并分别处理。
		int code = matcher.match(uri);

		switch (code) {
		case CODE_MEMOS:
			// 打开数据库。
			SQLiteDatabase db = manager.getWritableDatabase();

			// 更新。
			rows = db.update(DatabaseManager.TABLE_NAME_MEMO, values, selection, selectionArgs);
			break;
		}

		return rows;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int rows = 0;
		
		// 对传入的URI进行匹配并分别处理。
		int code = matcher.match(uri);

		switch (code) {
		case CODE_MEMOS:
			// 打开数据库。
			SQLiteDatabase db = manager.getWritableDatabase();

			// 删。
			rows = db.delete(DatabaseManager.TABLE_NAME_MEMO, selection, selectionArgs);
			break;
		}
		
		return rows;
	}
}
