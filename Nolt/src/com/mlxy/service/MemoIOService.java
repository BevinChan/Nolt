package com.mlxy.service;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mlxy.database.DatabaseManager;
import com.mlxy.info.Properties;
import com.mlxy.model.Memo;

/** 服务类，处理笔记数据的CRUD。*/
public class MemoIOService {
	/** 负责笔记数据的存取。*/
	ContentResolver resolver;

	public MemoIOService(Context context) {
		resolver = context.getContentResolver();
	}
	
	/** 获取并封装所有的笔记信息。*/
	public ArrayList<Memo> getAllMemos() {
		// 准备要返回的数组。
		ArrayList<Memo> memoList = new ArrayList<Memo>();
		
		// 向提供者查询，得到返回游标。
		Cursor cursor = resolver.query(Properties.URI_MEMOS,
											   new String[]{DatabaseManager.COLUMN_NAME_MEMO_ID,
															DatabaseManager.COLUMN_NAME_MEMO_TITLE,
															DatabaseManager.COLUMN_NAME_MEMO_CONTENT },
											   null, null, null);
		
		// 遍历游标。
		while (cursor.moveToNext()) {
			// 准备Memo对象。
			Memo memo = new Memo();
			
			// 取值。
			int id = cursor.getInt(0);
			String title = cursor.getString(1);
			String content = cursor.getString(2);
			
			// 设置。
			memo.id = id;
			memo.title = title;
			memo.content = content;
			
			// 加进列表。
			memoList.add(memo);
		}
		
		return memoList;
	}
	
	/** 保存一个新的笔记信息。*/
	public void createNewMemo(String title, String content) {
		// 填装数据。
		ContentValues values = new ContentValues();
		
		// 如果是空值就填空字符串，null会引起空指针异常。
		values.put(DatabaseManager.COLUMN_NAME_MEMO_TITLE, title == null ? "" : title);
		values.put(DatabaseManager.COLUMN_NAME_MEMO_CONTENT, content == null ? "" : content);
		
		// 发射！
		resolver.insert(Properties.URI_MEMOS, values);
	}
	
	/** 修改一个已有的笔记信息。*/
	public void modifyMemo(int id, String title, String content) {
		// 填装数据。
		ContentValues values = new ContentValues();
		
		// 如果是空值就填空字符串，null会引起空指针异常。
		values.put(DatabaseManager.COLUMN_NAME_MEMO_ID, id);
		values.put(DatabaseManager.COLUMN_NAME_MEMO_TITLE, title == null ? "" : title);
		values.put(DatabaseManager.COLUMN_NAME_MEMO_CONTENT, content == null ? "" : content);
		
		// 执行更新。
		resolver.update(Properties.URI_MEMOS,
						values,
						DatabaseManager.COLUMN_NAME_MEMO_ID + " = ?",
						new String[]{id+""});
	}
	
	/** 删除一条笔记信息。*/
	public void deleteMemo(int id) {
		// 直接删。
		resolver.delete(Properties.URI_MEMOS,
						DatabaseManager.COLUMN_NAME_MEMO_ID + " = ?",
						new String[]{id+""});
	}
}
