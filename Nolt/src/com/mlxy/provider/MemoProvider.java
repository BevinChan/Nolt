package com.mlxy.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mlxy.database.DatabaseManager;

/** �ʼ����ݵ��ṩ����*/
public class MemoProvider extends ContentProvider {
	/** �ʼ���Ϣ��ƥ���롣*/
	public static final int CODE_MEMOS = 233;
	
	private DatabaseManager manager;
	
	/** ƥ�䴫����URI��*/
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		matcher.addURI("memos", null, CODE_MEMOS);
	}
	
	public MemoProvider() {
	}

	@Override
	public boolean onCreate() {
		// ��ʼ�����ݿ��������
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
		// ׼��Ҫ���ص��αꡣ
		Cursor cursor = null;
		
		// �Դ����URI����ƥ�䲢�ֱ���
		int code = matcher.match(uri);
		
		switch (code) {
		case CODE_MEMOS:
			// �����ݿ⡣
			SQLiteDatabase database = manager.getReadableDatabase();
			
			// ִ�в�ѯ��
			cursor = database.query(DatabaseManager.TABLE_NAME_MEMO, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		
		return cursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// �Դ����URI����ƥ�䲢�ֱ���
		int code = matcher.match(uri);
		
		switch (code) {
		case CODE_MEMOS:
			// �����ݿ⡣
			SQLiteDatabase db = manager.getWritableDatabase();
			
			// ���롣
			db.insert(DatabaseManager.TABLE_NAME_MEMO, null, values);
			break;
		}
		return uri;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int rows = 0;
		
		// �Դ����URI����ƥ�䲢�ֱ���
		int code = matcher.match(uri);

		switch (code) {
		case CODE_MEMOS:
			// �����ݿ⡣
			SQLiteDatabase db = manager.getWritableDatabase();

			// ���¡�
			rows = db.update(DatabaseManager.TABLE_NAME_MEMO, values, selection, selectionArgs);
			break;
		}

		return rows;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int rows = 0;
		
		// �Դ����URI����ƥ�䲢�ֱ���
		int code = matcher.match(uri);

		switch (code) {
		case CODE_MEMOS:
			// �����ݿ⡣
			SQLiteDatabase db = manager.getWritableDatabase();

			// ɾ��
			rows = db.delete(DatabaseManager.TABLE_NAME_MEMO, selection, selectionArgs);
			break;
		}
		
		return rows;
	}
}
