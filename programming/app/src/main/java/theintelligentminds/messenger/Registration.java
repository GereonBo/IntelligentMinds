package theintelligentminds.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by Chris_1909 on 29.04.2015.
 */

public class Registration extends Activity {
    private Button buttonRegister;
    private EditText textfieldFirstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textfieldFirstname = (EditText) findViewById(R.id.textfieldFirstname);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Profile.class));
            }

        });

    }
}