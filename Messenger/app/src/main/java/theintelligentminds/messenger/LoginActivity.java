package theintelligentminds.messenger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.intelligentminds.client.ConnectionProvider;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
  private Button register;
  private Button login;
  private EditText email;
  private EditText password;
  private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    // Parse.initialize(this, "app-id", "client-key");

    EditText usernameField = (EditText) findViewById(R.id.textfieldEMail);
    register = (Button) findViewById(R.id.buttonRegister);
    login = (Button) findViewById(R.id.buttonLogin);

    email = (EditText) findViewById(R.id.textfieldEMail);
    password = (EditText) findViewById(R.id.textfieldPassword);

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AsyncDBAccess async = new AsyncDBAccess();
        async.execute();
      }
    });

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        //Intent intent = new Intent(LoginActivity.this, Registration.class);
        Intent intent = new Intent(LoginActivity.this, AddFriend.class);
        startActivity(intent);
      }

    });
  }

  class AsyncDBAccess extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
      String auth_token = provider.performLogin(email.getText().toString(), password.getText().toString());

      return auth_token;
    }

    @Override
    protected void onPostExecute(String auth_token) {
      super.onPostExecute(auth_token);

      if (auth_token.equals("")) {
        new AlertDialog.Builder(LoginActivity.this).setTitle("Login").setMessage("Login failed")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {

              }
            }).show();
      }
      else {
        new AlertDialog.Builder(LoginActivity.this).setTitle("Login").setMessage("Login successful")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(LoginActivity.this, Profile.class);
                startActivity(intent);
              }
            }).show();
      }
    }
  }
}
