package com.mlxy.service;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mlxy.database.DatabaseManager;
import com.mlxy.info.Properties;
import com.mlxy.model.Memo;

/** �����࣬����ʼ����ݵ�CRUD��*/
public class MemoIOService {
	/** ����ʼ����ݵĴ�ȡ��*/
	ContentResolver resolver;

	public MemoIOService(Context context) {
		resolver = context.getContentResolver();
	}
	
	/** ��ȡ����װ���еıʼ���Ϣ��*/
	public ArrayList<Memo> getAllMemos() {
		// ׼��Ҫ���ص����顣
		ArrayList<Memo> memoList = new ArrayList<Memo>();
		
		// ���ṩ�߲�ѯ���õ������αꡣ
		Cursor cursor = resolver.query(Properties.URI_MEMOS,
											   new String[]{DatabaseManager.COLUMN_NAME_MEMO_ID,
															DatabaseManager.COLUMN_NAME_MEMO_TITLE,
															DatabaseManager.COLUMN_NAME_MEMO_CONTENT },
											   null, null, null);
		
		// �����αꡣ
		while (cursor.moveToNext()) {
			// ׼��Memo����
			Memo memo = new Memo();
			
			// ȡֵ��
			int id = cursor.getInt(0);
			String title = cursor.getString(1);
			String content = cursor.getString(2);
			
			// ���á�
			memo.id = id;
			memo.title = title;
			memo.content = content;
			
			// �ӽ��б�
			memoList.add(memo);
		}
		
		return memoList;
	}
	
	/** ����һ���µıʼ���Ϣ��*/
	public void createNewMemo(String title, String content) {
		// ��װ���ݡ�
		ContentValues values = new ContentValues();
		
		// ����ǿ�ֵ������ַ�����null�������ָ���쳣��
		values.put(DatabaseManager.COLUMN_NAME_MEMO_TITLE, title == null ? "" : title);
		values.put(DatabaseManager.COLUMN_NAME_MEMO_CONTENT, content == null ? "" : content);
		
		// ���䣡
		resolver.insert(Properties.URI_MEMOS, values);
	}
	
	/** �޸�һ�����еıʼ���Ϣ��*/
	public void modifyMemo(int id, String title, String content) {
		// ��װ���ݡ�
		ContentValues values = new ContentValues();
		
		// ����ǿ�ֵ������ַ�����null�������ָ���쳣��
		values.put(DatabaseManager.COLUMN_NAME_MEMO_ID, id);
		values.put(DatabaseManager.COLUMN_NAME_MEMO_TITLE, title == null ? "" : title);
		values.put(DatabaseManager.COLUMN_NAME_MEMO_CONTENT, content == null ? "" : content);
		
		// ִ�и��¡�
		resolver.update(Properties.URI_MEMOS,
						values,
						DatabaseManager.COLUMN_NAME_MEMO_ID + " = ?",
						new String[]{id+""});
	}
	
	/** ɾ��һ���ʼ���Ϣ��*/
	public void deleteMemo(int id) {
		// ֱ��ɾ��
		resolver.delete(Properties.URI_MEMOS,
						DatabaseManager.COLUMN_NAME_MEMO_ID + " = ?",
						new String[]{id+""});
	}
}
