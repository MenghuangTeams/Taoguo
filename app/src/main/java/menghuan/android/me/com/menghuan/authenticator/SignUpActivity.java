package menghuan.android.me.com.menghuan.authenticator;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import menghuan.android.me.com.menghuan.R;
import menghuan.android.me.com.menghuan.client.NetworkUtilities;

public class SignUpActivity extends AppCompatActivity {
    /** The tag used to log to adb console. */
    private static final String TAG = "SignUpActivity";
    // UI references.
    private EditText mUserNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mPhoneView;
    private String mUserName;
    private String mEmail;
    private String mPassword;
    private String mError;
    private String mMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //StatusBarCompat.compat(this, Color.parseColor("#0077D9"));
        // Set up view
        mEmailView = (EditText) findViewById(R.id.user_email);
        mUserNameView = (EditText) findViewById(R.id.user_name);
        mPasswordView = (EditText) findViewById(R.id.user_password);
        mPhoneView = (EditText) findViewById(R.id.user_phone);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==R.id.action_save){
            boolean flag=save();
            if(flag==true) {
                finish();
            }else{
                Toast.makeText(SignUpActivity.this,"注册成功!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private UserSignUpTask mSingUpTask = null;
    private boolean save(){
        //TODO Add
        if(checkInputAndPermission()){
            mPassword = mPasswordView.getText().toString();
            mEmail = mEmailView.getText().toString();
            mUserName = mUserNameView.getText().toString();
            mSingUpTask = new UserSignUpTask();
            mSingUpTask.execute();
        }
        return false;
    }
    /**
     * 检查输入、服务器权限或在本地偏好设置中保存的优先级。
     * 如果有表单错误（无效的 email，丢失的字段等），则会出现错误，并且不进行服务器检查。
     */
    private boolean checkInputAndPermission() {
        //TODO check
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        //在登录时保存. Email、password 和 name
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String fullname = mUserNameView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // 如果用户输入姓名，检查其有效性.
        if (TextUtils.isEmpty(fullname)){// && !isPhoneValid(password)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }
        // 如果用户输入一个密码，检查其有效性.
        if (TextUtils.isEmpty(password)){// && !isPhoneValid(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        // 检查 email 有效性.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            return true;
        }
    }
    private boolean isEmailValid(String email) {
        // 可以用你自己的逻辑来替换
        return email.contains("@");
    }
    //TODO check
    private boolean isNameValid(String name){
        return false;
    }
    private boolean isPhoneValid(String phone){
        return false;
    }
    /**
     * 检查输入、服务器权限或在本地偏好设置中保存的优先级。
     * 如果有表单错误（无效的 email，丢失的字段等），则会出现错误，并且不进行服务器检查。 */
    private boolean checkEmailExist() {
        //TODO check
        // Reset errors.
        mEmailView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (mMessage.equals("Sorry, this email already existed")) {
            mEmailView.setError(getString(R.string.error_email_exist));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            return true;
        }
    }
    /**
     * 完成注册的异步任务
     */
    public class UserSignUpTask extends AsyncTask<Void, Void, String> {
        String body = null;
        @Override
        protected String doInBackground(Void... params) {
            // 调用 NetworkUtilities 类注册.
            try {
                body = NetworkUtilities.signUp(mUserName,mEmail, mPassword);
                JSONObject json = new JSONObject(body.toString());
                mError = json.getString("error");
                mMessage = json.getString("message");
                return body;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                return null;
            }
        }
        @Override
        protected void onPostExecute(final String authToken) {
            // 完成注册，回到登录界面
            if(checkEmailExist())
            {
                Intent intent = AuthenticatorActivity.newIntent(SignUpActivity.this,mEmail);
                startActivity(intent);
                finish();
            }
        }
    }
}
