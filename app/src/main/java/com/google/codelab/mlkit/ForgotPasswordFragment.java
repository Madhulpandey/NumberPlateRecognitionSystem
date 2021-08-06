package com.google.codelab.mlkit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class ForgotPasswordFragment extends Fragment {

    private TextInputEditText Femail,Fpassword,FcPassword,FSeqAns;
    private TextInputLayout pLayout,cpLayout;
    private Button newPwButton,changePw;
    DatabaseHandler db;
    private List<User> userList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.forgot_fragment,container,false);

        Femail=view.findViewById(R.id.FEmailID);
        FSeqAns=view.findViewById(R.id.FsecQueAns);
        newPwButton=view.findViewById(R.id.newPwBtn);
        changePw=view.findViewById(R.id.changePwButton);
        Fpassword=view.findViewById(R.id.Fpassword_et);
        FcPassword=view.findViewById(R.id.Fcpassword_et);
        pLayout=view.findViewById(R.id.FLayoutPassword);
        cpLayout=view.findViewById(R.id.FLayoutCPassword);
        db=new DatabaseHandler(getActivity());

        userList=db.getAllCustomers();

        Spinner dropdown = view.findViewById(R.id.FoffSpinnerReg);
        String[] items = new String[]{"Select your Security question","SecQue1", "SecQue2","SecQue3","SecQue4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout
                .simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){

                }
                if(i==2){

                }
                if(i==3){

                }
                if(i==4){

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        newPwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(User us:userList){
                    //Log.d("FORGED", "onClick: "+us.getEmailId()+" "+us.getSecQue());
                    if(us.getEmailId().toLowerCase().equals(Objects.requireNonNull(Femail.getText()).toString().toLowerCase()) && us.getSecQue().toLowerCase().equals(FSeqAns.getText().toString().toLowerCase())){
                        Toast.makeText(getActivity(), "Oye Ho gaya Oye", Toast.LENGTH_SHORT).show();
                        changePw.setVisibility(View.VISIBLE);
                        pLayout.setVisibility(View.VISIBLE);
                        cpLayout.setVisibility(View.VISIBLE);
                        FcPassword.setVisibility(View.VISIBLE);
                        Fpassword.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newpw= Objects.requireNonNull(FcPassword.getText()).toString();
                db.changeUserPassword(Objects.requireNonNull(Femail.getText()).toString(),newpw);
            }
        });

        //mad@gmail.com Tommy
        return view;
    }
}
