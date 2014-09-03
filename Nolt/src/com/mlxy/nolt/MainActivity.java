package com.mlxy.nolt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/** 登录界面。*/
@SuppressWarnings("unused")
public class MainActivity extends Activity implements OnClickListener {
	private EditText editTextUserName;
	private EditText editTextPassword;
	
	private Button buttonLogin;
	
	private String userName;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 如果已经登录了。
		if (isLoggedIn()) {
			// 就跳转到笔记列表页。
			Intent intent = new Intent();
			intent.setClass(this, MemoListActivity.class);
			this.startActivity(intent);
			this.finish();
		}
		
		// 初始化控件。
		this.initWidgets();
	}
	
	/** 初始化控件。*/
	private void initWidgets() {
		this.editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		this.editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		
		this.buttonLogin = (Button) findViewById(R.id.buttonLogin);
		// 绑定监听器。
		buttonLogin.setOnClickListener(this);
	}
	
	/** 判断用户是否已经登录了。*/
	private boolean isLoggedIn() {
		boolean isLoggedIn = false;
		
		// 获取首选项。
		SharedPreferences pref = this.getPreferences(MODE_PRIVATE);
		
		// 获取已登录用户名。
		String user = pref.getString("user", null);
		
		// 如果用户已经登录了。
		if (userName != null) {
			isLoggedIn = true;
		}
		
		return isLoggedIn;
	}
	
	/** 向服务器进行登录验证。*/
	private boolean login(String userName, String password) {
		boolean succeeded = false;
		
		// TODO: 各种验证。
		
		// TODO: 为了测试，暂时绕过。
		return true;
//		return succeeded;
	}
	
	@Override
	public void onClick(View v) {
		// 获取用户输入的用户名和密码。
		userName = editTextUserName.getText().toString();
		password = editTextPassword.getText().toString();
		
		// 登录验证。
		boolean succeeded = login(userName, password);
		
		// 如果成功了。
		if (succeeded) {
			// 就跳转到笔记列表页。
			Intent intent = new Intent();
			intent.setClass(this, MemoListActivity.class);
			this.startActivity(intent);
			this.finish();
		} else {
			// TODO: 报错，重新输。
		}
	}
}
