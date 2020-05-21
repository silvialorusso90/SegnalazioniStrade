package com.example.segnalazionistrade.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.segnalazionistrade.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfiloActivity extends AppCompatActivity {

    public static final int GalleryPick = 1;

    private Button mPhotoModyfy;
    private CircleImageView mProfilePhoto;
    TextView mTxtName, mTxtSurname, mTxtEmail;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;


    private StorageReference profileImageRef;
    //private ProgressDialog loadingBar;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profilo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        profileImageRef = FirebaseStorage.getInstance().getReference().child("foto");

        initUI();

        retrieveUserInfo();

        mPhotoModyfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPhoto();
            }
        });

    }

    private void initUI() {
        mTxtName = (TextView) findViewById(R.id.txtName);
        mTxtSurname = (TextView) findViewById(R.id.txtSurname);
        mTxtEmail = (TextView) findViewById(R.id.txtEmail);

        mProfilePhoto = (CircleImageView) findViewById(R.id.set_profile_image);
        mPhotoModyfy = (Button) findViewById(R.id.btnSettings);


    }

    private void retrieveUserInfo() {

        databaseRef.child("Utenti").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("nome") &&
                                (dataSnapshot.hasChild("cognome"))  && (dataSnapshot.
                                hasChild("email")) && (dataSnapshot.hasChild("foto")))) {
                            String retrieveUserName = dataSnapshot.child("nome").getValue().toString();
                            String retrievesUserSurname = dataSnapshot.child("cognome").getValue().toString();
                            String retrieveUserEmail = dataSnapshot.child("email").getValue().toString();
                            String retrieveUserImage = dataSnapshot.child("foto").getValue().toString();

                            mTxtName.setText(retrieveUserName);
                            mTxtSurname.setText(retrievesUserSurname);
                            mTxtEmail.setText(retrieveUserEmail);
                            Picasso.get().load(retrieveUserImage).into(mProfilePhoto);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("nome") &&
                                (dataSnapshot.hasChild("cognome"))  && (dataSnapshot.
                                hasChild("email")))) {
                            String retrieveUserName = dataSnapshot.child("nome").getValue().toString();
                            String retrievesUserSurname = dataSnapshot.child("cognome").getValue().toString();
                            String retrieveUserEmail = dataSnapshot.child("email").getValue().toString();

                            mTxtName.setText(retrieveUserName);
                            mTxtSurname.setText(retrievesUserSurname);
                            mTxtEmail.setText(retrieveUserEmail);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void modifyPhoto() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null) {
            Uri ImageUri = data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                final Uri resultUri = result.getUri();
                final StorageReference filePath = profileImageRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.
                        TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUrl = uri.toString();
                                databaseRef.child("Utenti").child(currentUserID).child("foto")
                                        .setValue(downloadUrl).addOnCompleteListener
                                        (new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfiloActivity.this,
                                        "Profile image stored to firebase database successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(ProfiloActivity.this,
                                                    "Error Occurred..." + message,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    }
}
