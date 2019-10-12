package com.example.googleauthentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
    List<AuthUI.IdpConfig> providers;

    Button btn_sign_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       btn_sign_out=(Button)findViewById(R.id.btn);

        btn_sign_out.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View view) {

                                                AuthUI.getInstance()
                                                        .signOut(MainActivity.this)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                            btn_sign_out.setEnabled(false);
                                                            showSignInOptions();

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }

                                        });

        providers= Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );


        showSignInOptions();
        }

    private void showSignInOptions() {    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                     .setTheme(R.style.MyTheme)
                                     .build(),MY_REQUEST_CODE
                                     );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            //Get User

            FirebaseUser user=  FirebaseAuth.getInstance().getCurrentUser();
            //Show email on toast

            Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
            //Set button sign out


            btn_sign_out.setEnabled(true);
        }
        else
        {
            Toast.makeText(this, "some thing went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}


