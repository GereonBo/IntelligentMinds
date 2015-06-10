package theintelligentminds.messenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.User;

/**
 * Created by Chris_1909 on 06.05.2015.
 */
public class EditProfile extends ActionBarActivity {
  private Button save;
  private Button back;
  private EditText firstName;
  private TextView gender;
  private EditText lastName;
  private TextView email;
  private EditText aboutMe;
  private ConnectionProvider provider = ConnectionProvider.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.editprofile);

    save = (Button) findViewById(R.id.buttonSave);
    back = (Button) findViewById(R.id.buttonBack);
    firstName = (EditText) findViewById(R.id.editTextFirstname);
    gender = (TextView) findViewById(R.id.viewTextGender);
    lastName = (EditText) findViewById(R.id.editTextLastname);
    email = (TextView) findViewById(R.id.viewTextMail);
    aboutMe = (EditText) findViewById(R.id.editTextAbout_Me);

    AsyncProfileInformation asyncProInfos = new AsyncProfileInformation();
    asyncProInfos.execute();

    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(EditProfile.this, Profile.class));

      }

    });

    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          AsyncUdpateProfileInformation asyncUdpateProfileInformation = new AsyncUdpateProfileInformation();

          User user = new User();
          user.setLastName(lastName.getText().toString());
          user.setFirstName(firstName.getText().toString());
          user.setAboutMe(aboutMe.getText().toString());

          asyncUdpateProfileInformation.execute(user);
      }

    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_editprofile, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    Intent intent = null;

    switch (item.getItemId()) {

      case R.id.options:
        intent = new Intent(EditProfile.this, Options.class);
        break;
      default:
        intent = null;
    }

    startActivity(intent);

    return super.onOptionsItemSelected(item);
  }

  class AsyncProfileInformation extends AsyncTask<String, Void, User> {
    @Override
    protected User doInBackground(String... strings) {
      User user = provider.getUserInformation();
      return user;
    }

    @Override
    protected void onPostExecute(User user) {
      super.onPostExecute(user);
      lastName.setText(user.getLastName());
      firstName.setText(user.getFirstName());
      email.setText(user.getEmail());
      gender.setText(user.getGender());
      aboutMe.setText(user.getAboutMe());
    }
  }

  class AsyncUdpateProfileInformation extends AsyncTask<User, Void, Boolean> {
    @Override
    protected Boolean doInBackground(User... users) {
      User user = users[0];
      boolean succesful = provider.updateUser(user.getFirstName(), user.getLastName(), user.getAboutMe());
      return succesful;
    }

    @Override
    protected void onPostExecute(Boolean succesful) {
      super.onPostExecute(succesful);
      if (succesful) {
        new AlertDialog.Builder(EditProfile.this).setTitle("Edit Profile").setMessage("Edit Profile successful")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(EditProfile.this, Profile.class);
                    startActivity(intent);
                }
            }).show();
      }
      else {
        new AlertDialog.Builder(EditProfile.this).setTitle("Edit Profile").setMessage("Edit Profile failed")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();

      }
    }
  }

}
