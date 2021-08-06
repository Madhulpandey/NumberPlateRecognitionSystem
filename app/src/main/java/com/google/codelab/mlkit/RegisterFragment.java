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
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class RegisterFragment extends Fragment {
    private TextInputEditText fname,lname,email,pwd,cpwd,secQueAns;
    DatabaseHandler db;
    private Button createAcc;
    int secQueId;
    CallBackFragment callBackFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register_fragment,container,false);

        fname=view.findViewById(R.id.fname_et);
        lname=view.findViewById(R.id.lname_et);
        email=view.findViewById(R.id.email_et);
        pwd=view.findViewById(R.id.password_et);
        cpwd=view.findViewById(R.id.cpassword_et);
        secQueAns=view.findViewById(R.id.secQueAns);
        createAcc=view.findViewById(R.id.createAccBtn);

        db=new DatabaseHandler(getActivity());

        Spinner dropdown = view.findViewById(R.id.offSpinnerReg);
        String[] items = new String[]{"Select your Security question","SecQue1", "SecQue2","SecQue3","SecQue4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout
                .simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    secQueId=1;
                }
                if(i==2){
                    secQueId=2;
                }
                if(i==3){
                    secQueId=3;
                }
                if(i==4){
                    secQueId=4;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwd.length()<=8){
                    Toast.makeText(getActivity(), "Password is too short", Toast.LENGTH_SHORT).show();
                }
                if(fname.getText()==null||lname.getText()==null||email.getText()==null||pwd.getText()==null||cpwd.getText()==null){
                    Toast.makeText(getActivity(), "Some or all fields are empty", Toast.LENGTH_SHORT).show();
                }
//                if(pwd.getText()!=cpwd.getText()){
//                    Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT).show();
//                }
                else{
                    User user=new User();
                    user.setFname(Objects.requireNonNull(fname.getText()).toString());
                    user.setLname(Objects.requireNonNull(lname.getText()).toString());
                    user.setEmailId(Objects.requireNonNull(email.getText()).toString());
                    user.setPwd(Objects.requireNonNull(pwd.getText()).toString());
                    user.setSecQue(Objects.requireNonNull(secQueAns.getText()).toString());
                    user.setSecQueID(secQueId);

                    db.addUser(user);
                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();

                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();

                }
                List<User> users=db.getAllCustomers();
                for(User user1:users){
                    Log.d("Register fragment", "onClick: "+user1.getEmailId()+user1.getSecQue()+user1.getSecQueID());
                }

            }

        });

        return view;
    }

    public void setCallBackFragment(CallBackFragment callBackFragment){
        this.callBackFragment=callBackFragment;
    }
}

