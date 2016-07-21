package com.developers.t_free;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton mSigninButton;
    private Intent intent;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static  final int RC_SIGN_IN=9001;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "GoogleActivity";
    private ProgressDialog mProgress;
    private Intent mIntent;
    private String mail;
    String url;
    HttpURLConnection ur=null;
    AlertDialog.Builder builder1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button b=(Button)findViewById(R.id.signout);
        mSigninButton= (SignInButton) findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("859862748367-hc8ul1doqh9hmflbpoq9lic9ns22j3kh.apps.googleusercontent.com").requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              signIn();
            }
        });
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  signOut();
            }
        });*/
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void signIn(){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut(){
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(0);
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                mail=account.getEmail();
                DataHolder.setMail(mail);
                checkuser(mail);
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(this,"Sign in Failed",Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        mProgress=new ProgressDialog(this);
        mProgress.setMessage("Loading....");
        mProgress.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        updateUI(1);
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        mProgress.dismiss();
                    }
                });
    }
    private void updateUI(int flag){
        if(flag==1){
           intent=new Intent(this,Login.class);
           startActivity(intent);
        }
        else{
            Toast.makeText(this,"sign out",Toast.LENGTH_LONG).show();
        }
    }
    private void checkuser(String ma){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaanbbbbbbbbbbbbbbbbbbbbbbbbba"+ma);
        url="http://10.1.32.50:8500/check/mail="+ma;
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u=new URL(url);
                    ur=(HttpURLConnection)u.openConnection();
                    ur.setRequestMethod("GET");
                    ur.setRequestProperty("Content-length", "0");
                    ur.setUseCaches(false);
                    ur.setAllowUserInteraction(false);
                    ur.connect();
                    BufferedReader br = new BufferedReader(new InputStreamReader(ur.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    System.out.println("dataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+sb);
                    JSONObject root=new JSONObject(sb.toString());
                    String bool=root.getString("bool");
                    System.out.println("saaaaaaaaaaaaaaaaaaaaaa "+bool);
                    if(bool.equals("1")){
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaas");
                       Intent ii = new Intent(getApplicationContext(),Sorry.class);
                        startActivity(ii);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    ur.disconnect();
                }
            }
        });
        t.start();
    }

    private void dialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Scene hogyaaaaaaaaaaaaa yaar");
        builder.show();
    }
}