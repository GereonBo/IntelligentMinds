package messenger.theintelligentminds.messenger;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import at.intelligentminds.client.ConnectionProvider;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;

/**
 * Created by Chris_1909 on 29.04.2015.
 */

public class Registration extends Activity {
  private Button register;
  private EditText firstName;
  private EditText lastName;
  private EditText email;
  private EditText password;
  private ConnectionProvider provider = ConnectionProvider.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registration);

    register = (Button) findViewById(R.id.buttonRegister);
    firstName = (EditText) findViewById(R.id.textfieldFirstname);
    lastName = (EditText) findViewById(R.id.textfieldLastname);
    email = (EditText) findViewById(R.id.textfieldEMail);
    password = (EditText) findViewById(R.id.textfieldPassword);

    register.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String message;

        ConnectionProvider.RegisterResponse response = provider.register(email.getText().toString(),
                password.getText().toString(), "male", firstName.getText().toString(),
                lastName.getText().toString());



        switch(response) {
            case EMAIL: message = "wrong email address"; break;
            case ERROR: message = "an error has occurred"; break;
            default: case MISC_ERROR: message = "an unexpected error has occurred"; break;
            case NAME: message = "invalid format at name"; break;
            case PASSWORD: message = "invalid format at name"; break;
            case SUCCESS: message = "Registration has been successful"; break;
            case USER_EXISTS: message = "The user exists already"; break;

        }

        new AlertDialog.Builder(getApplicationContext()).setTitle("Invalid Input").setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
      }

    });

  }

}