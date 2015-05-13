package messenger.theintelligentminds.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by Chris_1909 on 29.04.2015.
 */
public class Profile extends Activity {
  private TextView Lastname;
  private TextView Firstname;
  private Button Edit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile);

    Edit = (Button) findViewById(R.id.buttonEdit);
    Lastname = (TextView) findViewById(R.id.textfieldLastname);
    Firstname = (TextView) findViewById(R.id.textfieldFirstname);
    /*
     * String a;
     * 
     * Intent intent = getIntent(); if (intent != null) { Bundle bundle =
     * intent.getExtras(); if (bundle != null) { a =
     * bundle.getStringExtra("key"); Firstname.setText(a);
     * 
     * }}
     */
    Edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(Profile.this, EditProfile.class));
      }

    });
  }

}
