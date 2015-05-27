package theintelligentminds.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        Intent intent = new Intent(LoginActivity.this, Registration.class);
        startActivity(intent);
      }

    });
  }

  class AsyncDBAccess extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
      String authToken = provider.performLogin(email.getText().toString(), password.getText().toString());

      return authToken;
    }

    @Override
    protected void onPostExecute(final String authToken) {
      super.onPostExecute(authToken);

      if (authToken.equals("")) {
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
                Intent intent = new Intent(LoginActivity.this, FriendView.class);
                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
              }
            }).show();
      }
    }
  }
}
