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

/** 编辑笔记界面，新建和编辑两功能通用。*/
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
		
		// 初始化服务。
		this.service = new MemoIOService(this);
		
		// 初始化内部属性。
		this.initParams();
		
		// 初始化控件。
		this.initWidgets();
		
		// 获取传来的intent。
		Intent intent = this.getIntent();
		
		// 用方法分析。
		this.analyseIntent(intent);
	}
	
	/** 初始化属性。*/
	private void initParams() {
		id = -1;
		title = null;
		content = null;
	}
	
	/** 初始化控件。*/
	private void initWidgets() {
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextContent = (EditText) findViewById(R.id.editTextContent);
		
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(this);
	}
	
	/** 根据传来的Intent来做相应反应。*/
	private void analyseIntent(Intent intent) {
		// 从intent中取id参数。
		this.id = intent.getIntExtra("id", -1);
		
		if (id != -1) {
			// 如果有参数，说明也有title和content，是编辑命令。
			this.title = intent.getStringExtra("title");
			this.content = intent.getStringExtra("content");
			
			// 设置给两个编辑框。
			editTextTitle.setText(title);
			editTextContent.setText(content);
		} else {
			// 没有id参数说明也没有title和content，留空即可。
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSave:
			// 保存。
			if (id == -1) {
				createMemo();
				Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
			} else {
				updateMemo();
				Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		
		// 执行完成后自杀并启动列表页。
		Intent intent = new Intent(this, MemoListActivity.class);
		this.finish();
		startActivity(intent);
	}
	
	/** 新建笔记。*/
	private void createMemo() {
		// 从编辑框中取值。
		String title = editTextTitle.getText().toString();
		String content = editTextContent.getText().toString();
		
		// 由服务层添加数据。
		service.createNewMemo(title, content);
	}
	
	/** 更新笔记。*/
	private void updateMemo() {
		// 从编辑框中取值。
		String title = editTextTitle.getText().toString();
		String content = editTextContent.getText().toString();
		
		// 由服务层修改数据。
		service.modifyMemo(id, title, content);
	}
}
