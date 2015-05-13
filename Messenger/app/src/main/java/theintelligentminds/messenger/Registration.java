package theintelligentminds.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import at.intelligentminds.client.ConnectionProvider;

/**
 * Created by Chris_1909 on 29.04.2015.
 */

public class Registration extends Activity {
  private Button register;
  private EditText firstName;
  private EditText lastName;
  private EditText email;
  private EditText password;
  private RadioGroup radioSexGroup;
  private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registration);

    register = (Button) findViewById(R.id.buttonRegister);
    firstName = (EditText) findViewById(R.id.textfieldFirstname);
    lastName = (EditText) findViewById(R.id.textfieldLastname);
    email = (EditText) findViewById(R.id.textfieldEMail);
    password = (EditText) findViewById(R.id.textfieldPassword);
    radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

    register.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String message;
        AsyncDBAccess async = new AsyncDBAccess();
        async.execute();
      }
    });
  }

  class AsyncDBAccess extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
      String message;
      String sex = "";

      int selectedId = radioSexGroup.getCheckedRadioButtonId();

      switch (selectedId) {
        case R.id.radioButtonFemale:
          sex = "female";
          break;
        case R.id.radioButtonMale:
          sex = "male";
          break;
        default:
          return "No sex selected";
      }

      ConnectionProvider.RegisterResponse response = provider.register(email.getText().toString(), password.getText()
          .toString(), "male", firstName.getText().toString(), lastName.getText().toString());

      switch (response) {
        case EMAIL:
          message = "wrong email address";
          break;
        case ERROR:
          message = "an error has occurred";
          break;
        case NAME:
          message = "invalid format at name";
          break;
        case PASSWORD:
          message = "Password does not meet requirements";
          break;
        case SUCCESS:
          message = "Registration has been successful";
          break;
        case USER_EXISTS:
          message = "The user exists already";
          break;
        default:
        case MISC_ERROR:
          message = "an unexpected error has occurred";
          break;
      }

      return message;
    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);
      new AlertDialog.Builder(Registration.this).setTitle("Registration").setMessage(s)
          .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
          }).show();
    }
  }
}