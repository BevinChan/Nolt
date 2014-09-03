package com.mlxy.nolt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/** ��¼���档*/
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
		
		// ����Ѿ���¼�ˡ�
		if (isLoggedIn()) {
			// ����ת���ʼ��б�ҳ��
			Intent intent = new Intent();
			intent.setClass(this, MemoListActivity.class);
			this.startActivity(intent);
			this.finish();
		}
		
		// ��ʼ���ؼ���
		this.initWidgets();
	}
	
	/** ��ʼ���ؼ���*/
	private void initWidgets() {
		this.editTextUserName = (EditText) findViewById(R.id.editTextUserName);
		this.editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		
		this.buttonLogin = (Button) findViewById(R.id.buttonLogin);
		// �󶨼�������
		buttonLogin.setOnClickListener(this);
	}
	
	/** �ж��û��Ƿ��Ѿ���¼�ˡ�*/
	private boolean isLoggedIn() {
		boolean isLoggedIn = false;
		
		// ��ȡ��ѡ�
		SharedPreferences pref = this.getPreferences(MODE_PRIVATE);
		
		// ��ȡ�ѵ�¼�û�����
		String user = pref.getString("user", null);
		
		// ����û��Ѿ���¼�ˡ�
		if (userName != null) {
			isLoggedIn = true;
		}
		
		return isLoggedIn;
	}
	
	/** ����������е�¼��֤��*/
	private boolean login(String userName, String password) {
		boolean succeeded = false;
		
		// TODO: ������֤��
		
		// TODO: Ϊ�˲��ԣ���ʱ�ƹ���
		return true;
//		return succeeded;
	}
	
	@Override
	public void onClick(View v) {
		// ��ȡ�û�������û��������롣
		userName = editTextUserName.getText().toString();
		password = editTextPassword.getText().toString();
		
		// ��¼��֤��
		boolean succeeded = login(userName, password);
		
		// ����ɹ��ˡ�
		if (succeeded) {
			// ����ת���ʼ��б�ҳ��
			Intent intent = new Intent();
			intent.setClass(this, MemoListActivity.class);
			this.startActivity(intent);
			this.finish();
		} else {
			// TODO: ���������䡣
		}
	}
}
