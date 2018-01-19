package com.android.group.farmvillage.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.group.farmvillage.Activities.LoginActivity.MEDIA_TYPE;

public class RegistrerAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Variable
    String urlPostLogin = "";
    String errormsg;
    int errorcode;
    boolean isValidLogin;
    EditText etUserName,etPassword, etAdressMail ;
    Button bRegistrer;

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

            }
        });


    }

    private boolean makePostRegistrer(String sUsername, String sEmail, String sPassword, String name_faction) throws IOException {
        /*
        try {
            sPassword = toSHA1(sPassword.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        final OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("username", sEmail);
            postdata.put("password", sPassword);
            Log.d("error password? ",sPassword);
            Log.d("error username? ",sEmail);

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
        Response response = client.newCall(request).execute();
        String mMessage = response.body().string();
        if (response.isSuccessful()) {

            try {
                JSONObject json = new JSONObject(mMessage);

                // on récupere la reponse du serveur
                if (json.has("error")) {
                    Log.d("error :", " entree boucle");
                    errormsg = json.getJSONObject("error").getString("message");
                    Log.d("error :", " avant code");
                    errorcode = json.getJSONObject("error").getInt("code");
                    Log.d("Erreur ?", errormsg);
                } else {
                    Log.d("error avant iduser ", "oui");




                }
            } catch (Exception e) {
                Log.d("index_reponse :", "error");

                e.printStackTrace();
            }
        }
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
}
