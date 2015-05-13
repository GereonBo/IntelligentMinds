package theintelligentminds.messenger;

import android.app.Activity;
import android.app.Application;

import android.content.Intent;

import android.os.Bundle;
import theintelligentminds.messenger.Profile;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Chris_1909 on 29.04.2015.
 */

public class Registration extends Activity{
    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Register = (Button) findViewById(R.id.buttonRegister);
        final EditText FirstName = (EditText) findViewById(R.id.editTextFirstname);
/*
        FirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                                @Override
                                                public boolean onEditorAction(TextView textView, int i,KeyEvent keyEvent){
                                                    boolean handled = false;
                                                    if(i== EditorInfo.IME_ACTION_NEXT){
                                                        String inputText = textView.getText().toString();
                                                        Toast.makeText(Registration.this, "Your name is: " +
                                                        inputText, Toast.LENGTH_SHORT).show();
                                                    }
                                                    return handled;
                                                }


                                            });
*/
                Register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Registration.this, Profile.class);
                        //intent.putExtra("key", FirstName.getText().toString());
                        startActivity(intent);
                    }

                });





    }


}