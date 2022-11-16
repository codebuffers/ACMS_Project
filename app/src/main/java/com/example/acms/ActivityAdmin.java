package com.example.acms;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityAdmin extends AppCompatActivity {

    private Button buttonGnrt;
    private Bitmap bitmap;
    RecyclerView recyclerView;
    LinearLayout lineard;

    MainAdapterOfficerApproved mainAdapterOfficerApproved;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        buttonGnrt = findViewById(R.id.btnGnrtPDF);

        lineard = findViewById(R.id.lineard);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users")
                                .child("Visitors").orderByChild("visitorstatus").startAt("Approved").endAt("Approved\uf8ff"), Model.class)
                        .build();

        mainAdapterOfficerApproved = new MainAdapterOfficerApproved(options);
        recyclerView.setAdapter(mainAdapterOfficerApproved);

        buttonGnrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("size", "" +lineard.getWidth() + " " + lineard.getWidth());
                bitmap = LoadBitmap(lineard, lineard.getWidth(), lineard.getHeight());
                createReportPDF();
            }
        });

    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void createReportPDF() {
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        int convertWidth = (int)width,convertHeight = (int)height;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth,convertHeight,5).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap,convertWidth,convertHeight, true);
        canvas.drawBitmap(bitmap,0,0,null);
        document.finishPage(page);

        //Target pdf download starts here
        String targetPDF = "/sdcard/accessreport.pdf";
        File file;
        file = new File(targetPDF);
        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Report generated successfully", Toast.LENGTH_SHORT).show();
            openPdf();

        } catch (IOException e ) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong, please try again!"+e.toString(), Toast.LENGTH_SHORT).show();
            document.close();

        }

    }

    private void openPdf() {
        File file = new File("/sdcard/accessreport.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri,"application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No Report for viewing!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterOfficerApproved.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapterOfficerApproved.startListening();
    }
}