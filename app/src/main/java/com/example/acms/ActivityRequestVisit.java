package com.example.acms;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class ActivityRequestVisit extends AppCompatActivity {

    //calling the views
    private Button SubmitRequestBtn;
    Button btnGenerateTicket;
    private ImageView imageView;
    private ProgressBar progressBar;

    EditText VisitorName;
    EditText VisitorIC;
    EditText VisitorPhone;
    EditText VisitDate;
    EditText VisitTime;
    EditText VisitExitTime;
    EditText VisitReason;
    TextView VisitorTicket;


    //calling the db(firebase realtime database)
    private DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dbacms-36021-default-rtdb.firebaseio.com");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    //image uri to get img after its stored in db storage(firebase storage)
    private Uri imageUri;

    //calling datepicker...
    DatePickerDialog pickerDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_visit);

        //finding the ids for all used values/variables...
        SubmitRequestBtn = findViewById(R.id.SubmitRequestBtnId);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.VisitorImageId);
        VisitorName = findViewById(R.id.VisitorNameId);
        VisitorIC = findViewById(R.id.VisitorICId);
        VisitorPhone = findViewById(R.id.VisitorPhoneId);
        VisitDate = findViewById(R.id.VisitDateId);
        VisitTime = findViewById(R.id.VisitTimeId);
        VisitExitTime = findViewById(R.id.VisitExitTimeId);
        VisitReason = findViewById(R.id.VisitReasonId);

        btnGenerateTicket = findViewById(R.id.GenerateTicketBtnId);
        VisitorTicket = findViewById(R.id.GeneratedVisitorTicketId);

        progressBar.setVisibility(View.INVISIBLE);

        Toast.makeText(ActivityRequestVisit.this, "Send your Request Visit", Toast.LENGTH_LONG).show();

        //image picker from user(visitor) starts here
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        //image picker from user(visitor) ends here


        //date n time picker starts here

        //date picker below...
        VisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
                int chosenDay = calendar.get(Calendar.DAY_OF_MONTH);
                int chosenMonth = calendar.get(Calendar.MONTH);
                int chosenYear = calendar.get(Calendar.YEAR);

                //DatePicker dialog
                pickerDate = new DatePickerDialog(ActivityRequestVisit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        VisitDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, chosenYear, chosenMonth, chosenDay);

                pickerDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                pickerDate.show();
            }
        });

        //time picker below...
        VisitTime.setOnClickListener(new View.OnClickListener() {
            int chosenHour;
            int chosenMinute;
            int chosenHourEx;
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ActivityRequestVisit.this,

                        //this time picker suppose to be Holo if one of Latest API/Android Studio SDK used...
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                chosenHour = hourOfDay;

                                //adding the hours to enter time in the background...
                                chosenHourEx = hourOfDay+8;

                                chosenMinute = minute;
                                //store time into string
                                String time = chosenHour + ":" + chosenMinute;
                                String timeEx = chosenHourEx + ":" + chosenMinute;

                                //Initialize 24 h time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    Date dateEx = f24Hours.parse(timeEx);
                                    //Initialize 12 h time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    //Set selected time on text view
                                    VisitTime.setText(f12Hours.format(date));
                                    VisitExitTime.setText(f12Hours.format(dateEx));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(chosenHour, chosenMinute);
                timePickerDialog.show();
            }
        });

        //date n time pickers end here

        //ticket generator starts here
        btnGenerateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int generatedTicket = random.nextInt(9999999);
                VisitorTicket.setText(Integer.toString(generatedTicket));
            }
        });
        //ticket generator ends here

        //SubmitRequest starts here
        SubmitRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri == null) {
                    Toast.makeText(ActivityRequestVisit.this, "Please Upload Image", Toast.LENGTH_LONG).show();
                } else {
                    uploadToFirebase(imageUri);
                }
            }
        });
        //SubmitRequest ends here

        //Backt To the MainActivity Page function
        Button backToMain = findViewById(R.id.BackToMainBtnId);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityRequestVisit.this, MainActivity.class));
            }
        });
    }


    //uploading the visitor data starts here...
    private void uploadToFirebase(Uri imageUri) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        progressBar.setVisibility(View.INVISIBLE);

                        //filling the fields and check if empty...
                        if (TextUtils.isEmpty(VisitorName.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this,"Please enter your name", Toast.LENGTH_LONG).show();
                            VisitorName.setError("Name is required!");
                            VisitorName.requestFocus();
                        } else if (TextUtils.isEmpty(VisitorIC.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this,"Please enter your IC", Toast.LENGTH_LONG).show();
                            VisitorIC.setError("IC is required!");
                            VisitorIC.requestFocus();
                        } else if (TextUtils.isEmpty(VisitorPhone.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this,"Please enter your phone number", Toast.LENGTH_LONG).show();
                            VisitorPhone.setError("Phone number is required!");
                            VisitorPhone.requestFocus();
                        } else if (VisitorPhone.length() != 10) {
                            Toast.makeText(ActivityRequestVisit.this, "Please re-enter your phone number", Toast.LENGTH_LONG).show();
                            VisitorPhone.setError("Phone number must be 10 digits!");
                            VisitorPhone.requestFocus();
                        } else if (TextUtils.isEmpty(VisitReason.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this, "Please enter your visit reason", Toast.LENGTH_LONG).show();
                            VisitReason.setError("Visit reason is required!");
                            VisitReason.requestFocus();
                        } else if (TextUtils.isEmpty(VisitDate.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this, "Please enter date", Toast.LENGTH_LONG).show();
                            VisitDate.setError("Date is required!");
                            VisitDate.requestFocus();
                        } else if (TextUtils.isEmpty(VisitTime.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this, "Please enter time", Toast.LENGTH_LONG).show();
                            VisitTime.setError("Time is required!");
                            VisitTime.requestFocus();
                        } else if (TextUtils.isEmpty(VisitorTicket.getText().toString())) {
                            Toast.makeText(ActivityRequestVisit.this, "Please generate ticket number", Toast.LENGTH_LONG).show();
                            VisitorTicket.setError("Ticket is required!");
                            VisitorTicket.requestFocus();
                        }
                        else {

                            //if not empty start to upload to db...
                            String ticket = VisitorTicket.getText().toString();
                            String name = VisitorName.getText().toString();
                            String ic = VisitorIC.getText().toString();
                            String phone = VisitorPhone.getText().toString();
                            String dateV = VisitDate.getText().toString();
                            String timeV = VisitTime.getText().toString();
                            String timeVEx = VisitExitTime.getText().toString();
                            String status = "Pending";
                            String reason = VisitReason.getText().toString();
                            String statusreason = "Pending Security Officer Review";


                            //main uploading process starting here... but first image uploading to storage functions are below..
                            root.child("Users").child("Visitors").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.hasChild(ticket)) {
                                        //check if ticket num is not duplicated...
                                        Toast.makeText(ActivityRequestVisit.this, "Please re-generate a new ticket number!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //sending data to firebase realtime db
                                        //used ticket number as unique identity and other data comes under it
                                        root.child("Users").child("Visitors").child(ticket).child("visitorimage").setValue(uri.toString());
                                        root.child("Users").child("Visitors").child(ticket).child("visitorname").setValue(name);
                                        root.child("Users").child("Visitors").child(ticket).child("visitoric").setValue(ic);
                                        root.child("Users").child("Visitors").child(ticket).child("visitorphone").setValue(phone);
                                        root.child("Users").child("Visitors").child(ticket).child("visitdate").setValue(dateV);
                                        root.child("Users").child("Visitors").child(ticket).child("visittime").setValue(timeV);
                                        root.child("Users").child("Visitors").child(ticket).child("visitexittime").setValue(timeVEx);
                                        root.child("Users").child("Visitors").child(ticket).child("visitorstatus").setValue(status);
                                        root.child("Users").child("Visitors").child(ticket).child("visitorticket").setValue(ticket);
                                        root.child("Users").child("Visitors").child(ticket).child("visitreason").setValue(reason);
                                        root.child("Users").child("Visitors").child(ticket).child("statusreason").setValue(statusreason);

                                        Toast.makeText(ActivityRequestVisit.this, "Request Sent Successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), ActivityCheckStatus.class));
                                        finish();
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ActivityRequestVisit.this,"Sending request visit fail!",Toast.LENGTH_LONG).show();
            }
        });
    }

    //uploading the visitor data endss here...


    //upload image to storage..and pass the uri to overall user data uploader...
    private String getFileExtension(Uri imageUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(imageUri));
    }

    //upload image to storage..and pass the uri to overall user data uploader...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
