package theintelligentminds.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import at.intelligentminds.client.ConnectionProvider;

/**
 * Created by Chris_1909 on 29.04.2015.
 */

public class Registration extends Activity {
  private Button register;
  private Button changeBirthday;
  private EditText firstName;
  private EditText lastName;
  private EditText email;
  private EditText password;
  private RadioGroup radioSexGroup;
  private ConnectionProvider provider = ConnectionProvider.getInstance(AndroidFriendlyFeature.class);
  private TextView calendarView;
  private int year, month, day;
  private DatePickerDialog datePickerDialog;
  private SimpleDateFormat dateFormatter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registration);

    dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    register = (Button) findViewById(R.id.buttonRegister);
    changeBirthday = (Button) findViewById(R.id.buttonChangeBirthday);
    firstName = (EditText) findViewById(R.id.textfieldFirstname);
    lastName = (EditText) findViewById(R.id.textfieldLastname);
    email = (EditText) findViewById(R.id.textfieldEMail);
    password = (EditText) findViewById(R.id.textfieldPassword);
    radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
    calendarView = (TextView) findViewById(R.id.calendarView);

    final Calendar cal = Calendar.getInstance();
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH);
    day = cal.get(Calendar.DAY_OF_MONTH);

    calendarView.setText(dateFormatter.format(cal.getTime()));

    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

      public void onDateSet(DatePicker view, int y, int m, int d) {
        year = y;
        month = m;
        day = d;

        final Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);

        calendarView.setText(dateFormatter.format(cal.getTime()));
      }

    }, year, month, day);

    changeBirthday.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        datePickerDialog.show();
      }
    });

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
    private boolean registrationSuccessful = false;

    @Override
    protected String doInBackground(String... strings) {
      String message;
      String sex = "";
      registrationSuccessful = false;

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
          registrationSuccessful = true;
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
              if (registrationSuccessful) {
                Intent intent = new Intent(Registration.this, LoginActivity.class);
                startActivity(intent);
              }
            }
          }).show();
    }
  }
}