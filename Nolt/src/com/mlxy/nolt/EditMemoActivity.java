package com.mlxy.nolt;

import com.mlxy.service.MemoIOService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** �༭�ʼǽ��棬�½��ͱ༭������ͨ�á�*/
public class EditMemoActivity extends Activity implements OnClickListener {
	private MemoIOService service;
	
	private EditText editTextTitle;
	private EditText editTextContent;
	private Button buttonSave;

	private int id;
	private String title;
	private String content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_memo);
		
		// ��ʼ������
		this.service = new MemoIOService(this);
		
		// ��ʼ���ڲ����ԡ�
		this.initParams();
		
		// ��ʼ���ؼ���
		this.initWidgets();
		
		// ��ȡ������intent��
		Intent intent = this.getIntent();
		
		// �÷���������
		this.analyseIntent(intent);
	}
	
	/** ��ʼ�����ԡ�*/
	private void initParams() {
		id = -1;
		title = null;
		content = null;
	}
	
	/** ��ʼ���ؼ���*/
	private void initWidgets() {
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextContent = (EditText) findViewById(R.id.editTextContent);
		
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(this);
	}
	
	/** ���ݴ�����Intent������Ӧ��Ӧ��*/
	private void analyseIntent(Intent intent) {
		// ��intent��ȡid������
		this.id = intent.getIntExtra("id", -1);
		
		if (id != -1) {
			// ����в�����˵��Ҳ��title��content���Ǳ༭���
			this.title = intent.getStringExtra("title");
			this.content = intent.getStringExtra("content");
			
			// ���ø������༭��
			editTextTitle.setText(title);
			editTextContent.setText(content);
		} else {
			// û��id����˵��Ҳû��title��content�����ռ��ɡ�
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSave:
			// ���档
			if (id == -1) {
				createMemo();
				Toast.makeText(this, "�����ɹ�", Toast.LENGTH_SHORT).show();
			} else {
				updateMemo();
				Toast.makeText(this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		
		// ִ����ɺ���ɱ�������б�ҳ��
		Intent intent = new Intent(this, MemoListActivity.class);
		this.finish();
		startActivity(intent);
	}
	
	/** �½��ʼǡ�*/
	private void createMemo() {
		// �ӱ༭����ȡֵ��
		String title = editTextTitle.getText().toString();
		String content = editTextContent.getText().toString();
		
		// �ɷ����������ݡ�
		service.createNewMemo(title, content);
	}
	
	/** ���±ʼǡ�*/
	private void updateMemo() {
		// �ӱ༭����ȡֵ��
		String title = editTextTitle.getText().toString();
		String content = editTextContent.getText().toString();
		
		// �ɷ�����޸����ݡ�
		service.modifyMemo(id, title, content);
	}
}
