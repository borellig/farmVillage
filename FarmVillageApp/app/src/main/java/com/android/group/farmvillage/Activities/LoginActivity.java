package com.android.group.farmvillage.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.Users;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View bLostPassword;
    private volatile boolean isValidLogin;
    Village village;
    String errormsg;
    int errorcode;
    String urlPostLogin = "http://artshared.fr/andev1/distribue/api/auth/signin/?loop";
    public final int nbCase=30;

    //user
    Users user;

    //Connect with FB
    LoginButton lLoginButtonwithFB;
    CallbackManager CallbackManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());


        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        initializeControls();
        loginWithFB();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        bLostPassword = (Button)findViewById(R.id.nopassword);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        bLostPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AskNewPassword();
            }
        });
    }

    private void initializeControls(){
        CallbackManage = CallbackManager.Factory.create();
        lLoginButtonwithFB = (LoginButton)findViewById(R.id.login_button);

    }

    /**
     * Fonction pour se connecter avec facebook
     */
    private void loginWithFB(){
        LoginManager.getInstance().registerCallback(CallbackManage, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "KO : "+error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CallbackManage.onActivityResult(requestCode, resultCode, data);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValide(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public void AskNewPassword(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Redemander votre mots de passe");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //final DBHandler db1 = db;

        final EditText titleBox = new EditText(this);
        titleBox.setHint("Adresse mail");
        titleBox.setInputType(InputType.TYPE_CLASS_TEXT);

        layout.addView(titleBox);

        builder.setView(layout);

        // Configurations des boutons
        builder.setPositiveButton("Demander", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    /**/

                    String mailaddress = titleBox.getText().toString();
                    mailaddress = mailaddress.trim();
                    if(isEmailValide(mailaddress)==true){
                        Toast.makeText(getApplicationContext(), mailaddress, Toast.LENGTH_LONG).show();
                        /*action envoyer mdp sur l'adresse mail, voir si adresse mail non répertorié"*/
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Mauvaise adresse mail indiquée", Toast.LENGTH_LONG).show();

                    }


            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            Log.d("Email", mEmail);
            try {
                if(makePostLogin(mEmail,mPassword)==true){
                    if(village!=null) {
                        Log.d("error avant intent"," non");
                        Intent MainActivite = new Intent(LoginActivity.this, MainActivity.class);
                        MainActivite.putExtra("village", village);
                        Log.d("error avant MA"," non");
                        Log.d("error village :", String.valueOf(village));
                        startActivity(MainActivite);
                        finish();
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else{

                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            //sendDataConnection(mEmail,mPassword);

           /* for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(errormsg+errorcode);
                mPasswordView.requestFocus();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public static boolean isEmailValide(String email) {
        boolean isValid = false;

        /*String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }*/

        //pour test
        isValid=true;
        return isValid;
    }


    public static String toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Log.d("Sha1 : ", byteArrayToHexString(md.digest(convertme)));
        return byteArrayToHexString(md.digest(convertme));
    }


    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }


    /**
     * Envoi les données de connexion sur le serveur
     * @param sEmail l'email inseré par l'utilisateur - String
     * @param sPassword le password inseré par l'utilisateur - String
     * @return True si la connexion est bonne, false si erreur
     */
    private boolean makePostLogin(final String sEmail, String sPassword) throws IOException {
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

                    int iIDuser = json.getJSONObject("user").getInt("_id");
                    String sUUIDuser = json.getJSONObject("user").getString("globalId");
                    String sNameuser = json.getJSONObject("user").getString("username");
                    String sEmailuser = json.getJSONObject("user").getString("email");
                    String sFactionuser = json.getJSONObject("user").getString("faction");
                    user = new Users(iIDuser, sUUIDuser, sNameuser, sEmailuser, sFactionuser);
                    Log.d("User", String.valueOf(user));

                                            Log.d("error avant initi","oui");
                        village = initialisation(sUUIDuser);
                        Log.d("error apres initi","oui");

                        isValidLogin = true;



                }
            } catch (Exception e) {
                Log.d("index_reponse :", "error");

                e.printStackTrace();
            }
        }
        return isValidLogin;
    }

    private Village initialisation(String UUID) throws IOException {

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://artshared.fr/andev1/distribue/android/get_game.php?uid="+UUID)
                .build();
        Response response = client.newCall(request).execute();
        String mMessage = response.body().string();
        Village myVillage = null;
        if (response.isSuccessful()) {
            Log.d("error avant try ini", UUID);
            try {
                JSONObject jVillage = new JSONObject(mMessage);
                int iId = jVillage.getInt("iId");
                String sUUID = jVillage.getString("sUUID");
                String sName = jVillage.getString("sName");
                int iWood = jVillage.getInt("iWood");
                int iFood = jVillage.getInt("iFood");
                int iRock = jVillage.getInt("iRock");
                int iGold = jVillage.getInt("iGold");
                int iDefensePoint = jVillage.getInt("iDefensePoint");
                ArrayList<Building> listBuilding = new ArrayList<>();
                JSONArray jListBuilding = new JSONArray(jVillage.getString("building"));
                Date d = new Date();
                for (int i = 0; i < this.nbCase; i++) {
                    listBuilding.add(i, new Building(false, 0, TypeBuilding.Vide, i, d, 0));
                }
                if (jListBuilding != null) {
                    for (int i = 0; i < jListBuilding.length(); i++) {
                        JSONObject jBuilding = new JSONObject(jListBuilding.get(i).toString());
                        boolean bEnable;
                        if (jBuilding.getInt("bEnable") == 1) {
                            bEnable = true;
                        } else {
                            bEnable = false;
                        }

                        int iLevel = jBuilding.getInt("iLevel");
                        int iMilitaryCount = jBuilding.getInt("iMilitaryCount");
                        Date dConstruct = new Date(jBuilding.getLong("dConstruct"));
                        int typeBuilding = jBuilding.getInt("iId_typebuilding");
                        int index = jBuilding.getInt("iIndex");
                        TypeBuilding tb = TypeBuilding.values()[typeBuilding];
                        Building newB = new Building(bEnable, iLevel, tb, index, dConstruct, iMilitaryCount);
                        if (newB.isbEnable()) {//if(newB.getdConstruct().getTime()+dureeConstruction<new Date().getTime()){
                            listBuilding.set(index, newB);
                        } else {
                            Building tmpBuilding = new Building(false, iLevel, TypeBuilding.values()[typeBuilding], index, dConstruct, iMilitaryCount);
                            listBuilding.set(index, tmpBuilding);

                        }
                        listBuilding.get(index).setiId(jBuilding.getInt("iId"));
                    }
                }
                myVillage = new Village(iId, sUUID, sName, iWood, iFood, iRock, iGold, iDefensePoint, listBuilding);
                myVillage.setlLastmaj(jVillage.getLong("lastmaj"));
            } catch (Exception e) {
                Log.d("errorTry", "plantage");
                e.printStackTrace();
            }
            Log.d("error apres init", " oui");

        }
        return myVillage;

    }
}

