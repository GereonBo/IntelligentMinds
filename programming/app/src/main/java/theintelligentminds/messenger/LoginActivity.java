package theintelligentminds.messenger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{



  // UI references.
  public TextView textViewUserName;
  public EditText textViewPassword;
  public Button registerButton;
  public Button loginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login);
      addListenerOnButton();

  }

    public void addListenerOnButton(){
        registerButton = (Button) findViewById(R.id.buttonRegister);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();

            }
        });


    }
    performLogin(admin, admin);

    public void doLogin(){
    textViewUserName = (TextView) findViewById(R.id.textfieldUserName);
    textViewPassword = (EditText) findViewById(R.id.textfieldPassword);

        String user_name = textViewUserName.getText().toString();
        String password = textViewPassword.getText().toString();

        if ((CheckIfUserNameExists(user_name)) == true)
        {
            if ((CheckIfPasswordCorrect(user_name, password)) == true)
            {
                Toast.makeText(LoginActivity.this," Congratulations, you are logged in!",
                                    Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(LoginActivity.this,"OOOps wrong password, please try again!",
                        Toast.LENGTH_SHORT).show();
               }
        }
        else{
            Toast.makeText(LoginActivity.this, "This user name does not exist!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void doRegister(){

        Toast.makeText(LoginActivity.this," You clicked RegisterButton!",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, Registration.class));
    }

    public boolean CheckIfUserNameExists(String userName){
        return true;
    }

    public boolean CheckIfPasswordCorrect(String userName, String password){
        return true;
    }

}
