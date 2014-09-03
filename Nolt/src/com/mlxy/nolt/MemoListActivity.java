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

/** �ʼ��б�ҳ��*/
public class MemoListActivity extends Activity {
	/** �����ࡣ*/
	private MemoIOService service;
	
	/** �½��ʼǰ�ť��*/
	private Button buttonNewMemo;
	
	/** �ʼ��б�*/
	private ArrayList<Memo> memoList;
	/** �ʼǱ����б�ֻ������ʾ��*/
	private ArrayList<String> memoTitleList;
	
	/** �ʼ��б���ͼ��*/
	private ListView listViewMemoTitle;
	/** �ʼ��б���ͼ����������*/
	private ArrayAdapter<String> memoTitleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo_list);
		
		// ��ʼ�������ࡣ
		service = new MemoIOService(this);
		
		// ��ʼ���б����ݡ�
		this.initMemoList();
		this.initMemoTitleList();
		
		// ��ʼ���ؼ���
		this.initWidgets();
	}
	
	/** ��ʼ���ؼ���*/
	private void initWidgets() {
		// �½��ʼǰ�ť��
		buttonNewMemo = (Button) findViewById(R.id.buttonNewMemo);
		buttonNewMemo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���½��ʼ�ҳ�档
				Intent intent = new Intent();
				intent.setClass(MemoListActivity.this, EditMemoActivity.class);
				startActivity(intent);
			}
		});
		
		// ��������
		memoTitleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, memoTitleList);
		
		// �б���ͼ��
		listViewMemoTitle = (ListView) findViewById(R.id.listViewMemoTitle);
		// ������������
		listViewMemoTitle.setAdapter(memoTitleAdapter);
		// ������Ŀ�����������
		listViewMemoTitle.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// �򿪱༭ҳ�棬�����������ݡ�
				Intent intent = new Intent();
				intent.setClass(MemoListActivity.this, EditMemoActivity.class);
				intent.putExtra("id", memoList.get(position).id);
				intent.putExtra("title", memoList.get(position).title);
				intent.putExtra("content", memoList.get(position).content);
				startActivity(intent);
			}
		});
		// ������Ŀ������������
		listViewMemoTitle.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// �����Ի���Ҫ��ȷ��ɾ����
				new AlertDialog.Builder(MemoListActivity.this)
					.setTitle("ɾ��ȷ��")
					.setMessage("ȷ��Ҫɾ��["+memoTitleList.get(position)+"]��")
					.setNegativeButton("ȡ��", null)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// ���ȷ����ɾ��������λ�õ���Ŀ��
							memoTitleList.remove(position);
							// �����ݿ���ɾ����
							Memo memo = memoList.get(position);
							service.deleteMemo(memo.id);
							
							// �������档
							MemoListActivity.this.recreate();
						}
					})
					.show();
				
				return true;
			}
		});
	}
	
	/** ��ʼ���ʼ��б����ݡ�*/
	private void initMemoList() {
		// ��ʼ���ʼ��б�
		
		memoList = service.getAllMemos();
	}
	
	/** ��ʼ���ʼǱ����б����ݡ�*/
	private void initMemoTitleList() {
		memoTitleList = new ArrayList<String>();
		for (Memo m : memoList) {
			memoTitleList.add(m.title);
		}
	}
}
