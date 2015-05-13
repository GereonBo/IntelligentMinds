package messenger.theintelligentminds.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import at.intelligentminds.client.ConnectionProvider;


/**
 * A login screen that offers login via email/password.
 */
public class Login extends Activity {
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Parse.initialize(this, "app-id", "client-key");

        EditText usernameField = (EditText) findViewById(R.id.textfieldEMail);
        register = (Button) findViewById(R.id.buttonRegister);
        login = (Button) findViewById(R.id.buttonLogin);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Login.this, Profile.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }

        });
    }
}