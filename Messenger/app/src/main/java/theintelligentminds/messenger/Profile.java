package theintelligentminds.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import theintelligentminds.messenger.Registration;
import android.widget.EditText;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by Chris_1909 on 29.04.2015.
 */
public class Profile extends ActionBarActivity {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = null;

        switch(item.getItemId())
        {
            case R.id.friendlist:
                intent = new Intent(Profile.this, FriendView.class);
                break;
            case R.id.options:
                intent = new Intent(Profile.this, Options.class);
                break;
            default:
                intent = null;
        }

        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

}
