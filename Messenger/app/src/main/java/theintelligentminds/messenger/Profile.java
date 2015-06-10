package theintelligentminds.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import at.intelligentminds.client.ConnectionProvider;
import at.intelligentminds.client.User;

import android.widget.TextView;

/**
 * Created by Chris_1909 on 29.04.2015.
 */
public class Profile extends Activity {
  private ConnectionProvider provider = ConnectionProvider.getInstance();
  private TextView lastName;
  private TextView firstName;
  private TextView eMail;
  private TextView sex;
  private TextView aboutMe;
  private Button edit;
  private User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile);

    edit = (Button) findViewById(R.id.buttonEdit);
    lastName = (TextView) findViewById(R.id.textfieldLastname);
    firstName = (TextView) findViewById(R.id.textfieldFirstname);
    eMail = (TextView) findViewById(R.id.viewTextMail);
    aboutMe = (TextView) findViewById(R.id.editTextAbout_Me);

    user = provider.getUserInformation();

    lastName.setText(user.getLastName());
    firstName.setText(user.getFirstName());
    eMail.setText(user.getEmail());
    aboutMe.setText(user.getAboutMe());

    edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Profile.this, EditProfile.class));

        }

    });
  }

}
