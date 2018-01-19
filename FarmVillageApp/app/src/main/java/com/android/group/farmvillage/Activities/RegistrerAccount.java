package com.android.group.farmvillage.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.group.farmvillage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.group.farmvillage.Activities.LoginActivity.MEDIA_TYPE;

public class RegistrerAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Variable
    String urlPostLogin = "http://artshared.fr/andev1/distribue/api/auth/signup/";
    String errormsg;
    int errorcode;
    boolean isValidLogin;
    EditText etUserName,etPassword, etAdressMail ;
    Button bRegistrer;
    int iFactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_registration);

        //Association avec le layout
        final Spinner spinner = (Spinner) findViewById(R.id.faction_choice);
        etUserName = (EditText) findViewById(R.id.Username);
        etAdressMail = (EditText) findViewById(R.id.AddressMail);
        etPassword = (EditText) findViewById(R.id.Password);
        bRegistrer = (Button) findViewById(R.id.button_validate);


    // Création d'un ArrayAdapter pour utiliser la liste de faction
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.faction_name, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        bRegistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin(iFactionID++);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iFactionID = (int) spinner.getSelectedItemId();
                iFactionID++;
                Log.d("spinner id : ", String.valueOf(iFactionID));
            }
        });


    }

    private void attemptLogin(int idFaction) throws IOException {

        // Reset errors.
        etAdressMail.setError(null);
        etPassword.setError(null);
        etUserName.setError(null);

        View focusView = null;
        boolean cancel = false;

        // Store values at the time of the login attempt.
        String username = etUserName.getText().toString();
        String email = etAdressMail.getText().toString();
        String password = etPassword.getText().toString();


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etAdressMail.setError(getString(R.string.error_field_required));
            focusView = etAdressMail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etAdressMail.setError(getString(R.string.error_invalid_email));
            focusView = etAdressMail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if (makePostRegistrer(username,email,password,idFaction)==true){

            }
            else{
                etUserName.setError(errormsg+errorcode);
                etUserName.requestFocus();
                focusView.requestFocus();
            }
        }
    }



    private boolean makePostRegistrer(String sUsername, String sEmail, String sPassword, int idfaction) {
        final OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("username", sUsername);
            postdata.put("email", sEmail);
            postdata.put("password", sPassword);
            postdata.put("id_faction", idfaction);

        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());

        final Request request = new Request.Builder()
                .url(urlPostLogin)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Your Token")
                .addHeader("cache-control", "no-cache")
                .build();

        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                String mMessage = e.getMessage().toString();
                                                Log.w("failure Response", mMessage);
                                                //call.cancel();
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response)
                                                    throws IOException {

                                                String mMessage = response.body().string();
                                                if (response.isSuccessful()){
                                                    try {
                                                        JSONObject json = new JSONObject(mMessage);
                                                        Log.d("error apres reception ?", "non");

                                                        // on récupere la reponse du serveur
                                                        if (json.has("error")) {
                                                            errormsg = json.getJSONObject("error").getString("message");
                                                            errorcode = json.getJSONObject("error").getInt("code");
                                                            Log.d("retour error :", String.valueOf(json));
                                                            isValidLogin = false;
                                                        } else {

                                                            isValidLogin = true;

                                                        }
                                                    } catch (Exception e) {
                                                        Log.d("index_reponse :", "error");

                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
        );
        return isValidLogin;
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
