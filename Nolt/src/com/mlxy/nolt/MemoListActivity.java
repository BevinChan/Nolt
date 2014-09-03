package com.mlxy.nolt;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mlxy.model.Memo;
import com.mlxy.service.MemoIOService;

/** 笔记列表页。*/
public class MemoListActivity extends Activity {
	/** 服务类。*/
	private MemoIOService service;
	
	/** 新建笔记按钮。*/
	private Button buttonNewMemo;
	
	/** 笔记列表。*/
	private ArrayList<Memo> memoList;
	/** 笔记标题列表，只用于显示。*/
	private ArrayList<String> memoTitleList;
	
	/** 笔记列表视图。*/
	private ListView listViewMemoTitle;
	/** 笔记列表视图的适配器。*/
	private ArrayAdapter<String> memoTitleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo_list);
		
		// 初始化服务类。
		service = new MemoIOService(this);
		
		// 初始化列表内容。
		this.initMemoList();
		this.initMemoTitleList();
		
		// 初始化控件。
		this.initWidgets();
	}
	
	/** 初始化控件。*/
	private void initWidgets() {
		// 新建笔记按钮。
		buttonNewMemo = (Button) findViewById(R.id.buttonNewMemo);
		buttonNewMemo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打开新建笔记页面。
				Intent intent = new Intent();
				intent.setClass(MemoListActivity.this, EditMemoActivity.class);
				startActivity(intent);
			}
		});
		
		// 适配器。
		memoTitleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, memoTitleList);
		
		// 列表视图。
		listViewMemoTitle = (ListView) findViewById(R.id.listViewMemoTitle);
		// 设置适配器。
		listViewMemoTitle.setAdapter(memoTitleAdapter);
		// 设置项目点击监听器。
		listViewMemoTitle.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 打开编辑页面，传入所需数据。
				Intent intent = new Intent();
				intent.setClass(MemoListActivity.this, EditMemoActivity.class);
				intent.putExtra("id", memoList.get(position).id);
				intent.putExtra("title", memoList.get(position).title);
				intent.putExtra("content", memoList.get(position).content);
				startActivity(intent);
			}
		});
		// 设置项目长按监听器。
		listViewMemoTitle.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// 弹出对话框要求确认删除。
				new AlertDialog.Builder(MemoListActivity.this)
					.setTitle("删除确认")
					.setMessage("确定要删除["+memoTitleList.get(position)+"]吗？")
					.setNegativeButton("取消", null)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击确认则删除被长按位置的项目。
							memoTitleList.remove(position);
							// 从数据库中删除。
							Memo memo = memoList.get(position);
							service.deleteMemo(memo.id);
							
							// 重启界面。
							MemoListActivity.this.recreate();
						}
					})
					.show();
				
				return true;
			}
		});
	}
	
	/** 初始化笔记列表内容。*/
	private void initMemoList() {
		// 初始化笔记列表。
		
		memoList = service.getAllMemos();
	}
	
	/** 初始化笔记标题列表内容。*/
	private void initMemoTitleList() {
		memoTitleList = new ArrayList<String>();
		for (Memo m : memoList) {
			memoTitleList.add(m.title);
		}
	}
}
