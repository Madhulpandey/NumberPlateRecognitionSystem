package com.google.codelab.mlkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class OffenderActivity extends AppCompatActivity {

    private TextView off_fname;
    private TextView off_lname;
    private TextView off_email;
    private TextView off_state;
    private TextView off_vehicleNum;
    private Button sendMailBtn;
    private Button notifyOffender;
    String name;
    int fine;
    Offender offe;
    String offence;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offender);

        db=new DatabaseHandler(this);
        off_fname=findViewById(R.id.off_fname);
        off_lname=findViewById(R.id.off_lname);
        off_email=findViewById(R.id.off_Email);
        off_state=findViewById(R.id.off_State); //district number
        off_vehicleNum=findViewById(R.id.off_vehicleNum);
        sendMailBtn=findViewById(R.id.mailBtn);
        notifyOffender=findViewById(R.id.notifyRTO);

        String extractedString=getIntent().getExtras().getString("Extracted String");

        String []dets=extractedString.split(" ");
        //Log.d("OFFENDER0", "onCreate: "+dets[0]);

        off_vehicleNum.append(dets[1]);
        off_state.append(dets[0]);

        Offender offender=new Offender();
        offender.setOf_carnum("MH 12 DE 1433");
        offender.setOf_fname("Madhul");
        offender.setOf_lname("Pandey");
        offender.setOf_mail("abc@gmail.com");
        offender.setOf_state("12");
        offender.setOf_numplate("1433");

        //       offe=new Offender();
//        offe.setOf_carnum("KA 03 DE 1351");
//        offe.setOf_fname("Rahul");
//        offe.setOf_lname("kumar");
//        offe.setOf_mail("rahulk17@gmail.com");
//        offe.setOf_state("03");
//        offe.setOf_numplate("1351");

        offe=new Offender();
        offe.setOf_carnum("KA 51 M 8156");
        offe.setOf_fname("Suresh");
        offe.setOf_lname("Agarwal");
        offe.setOf_mail("agarwal2000@gmail.com");
        offe.setOf_state("51");
        offe.setOf_numplate("8156");

        //db.addOffender(offe);

        //db.deleteAllOffenders();

        List<Offender> offList=db.getAllOffender();

//        Log.d("DET VAL", "onCreate: "+dets[0]+"and"+dets[1]);

        for(Offender offend:offList){
            Log.d("OFFENDERLIST", "onCreate: "+offend.getOf_id()+offend.getOf_numplate()+offend.getOf_fname());


//            if(offend.getOf_numplate().equals(dets[1])) {
//
//                name = offend.getOf_fname();
//                off_fname.append(offend.getOf_fname());
//                off_lname.append(offend.getOf_lname());
//                off_email.append(offend.getOf_mail());
//                notifyOffender.setVisibility(View.INVISIBLE);
//            }
        }

        if(off_email.getText()=="Email:"){
            notifyOffender.setVisibility(View.VISIBLE);
        }

        sendMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            composeEmail(new String[]{off_email.getText().toString()},"Traffic Violation");
            }
        });

        Spinner dropdown = findViewById(R.id.offSpinner);
        String[] items = new String[]{"Select one of the following","Rash Driving", "Over-Speeding","Driving under the influence","Not wearing helmet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                 fine = 2000;
                 offence="Rash Driving";
                }
                if(i==2){
                     fine = 3000;
                     offence="Not wearing seat belt";
                }
                if(i==3){
                     fine = 4000;
                     offence="Driving under the influence";
                }
                if(i==4){
                    fine = 5000;
                    offence="Not wearing helmet";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        notifyOffender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, "rto.02-mh@gov.in");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Missing Data in Database");
                    intent.putExtra(Intent.EXTRA_TEXT, "Dear Sir/Madam"+"\n"+"This mail is to notify the respective authorities of data of a vehicle with vehicle number "+ off_vehicleNum.getText()+" being missing");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

            }
        });

    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "Dear "+name+"\n"+"Offence: "+offence+"\n"+"Fine: "+fine);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}