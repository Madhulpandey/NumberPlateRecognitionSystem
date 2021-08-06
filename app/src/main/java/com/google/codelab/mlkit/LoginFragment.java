package com.google.codelab.mlkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelab.mlkit.CallBackFragment;
import com.google.codelab.mlkit.DatabaseHandler;
import com.google.codelab.mlkit.R;
import com.google.codelab.mlkit.User;

import java.util.List;
import java.util.Objects;

public class LoginFragment extends Fragment {
    private Button loginBtn,registerBtn,forgotPassword;
    private TextInputLayout unameInputLayout,pwdInputLayout;
    private TextInputEditText uname,pwd;
    CallBackFragment callBackFragment;
    DatabaseHandler db;
    List<User> userList;
    User SucUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        loginBtn= view.findViewById(R.id.LoginBtn);
        registerBtn=view.findViewById(R.id.createAccBtn);
        forgotPassword=view.findViewById(R.id.forgotPassword);
        unameInputLayout=view.findViewById(R.id.inptLayoutUsername);
        pwdInputLayout=view.findViewById(R.id.inptLayoutPassword);
        uname=view.findViewById(R.id.username_et);
        pwd=view.findViewById(R.id.passwordL_et);


        db=new DatabaseHandler(getActivity());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= Objects.requireNonNull(uname.getText()).toString().trim();
                String password= Objects.requireNonNull(pwd.getText()).toString().trim();
                int count=0;
                if(pwd.getText()==null){
                    Toast.makeText(getActivity(), "Password field is empty", Toast.LENGTH_SHORT).show();
                }if(uname.getText() == null){
                    Toast.makeText(getActivity(), "Username field is empty", Toast.LENGTH_SHORT).show();
                }else{
                    userList=db.getAllCustomers();
                    for(User user:userList){
                        if(user.getEmailId().equals(email) && user.getPwd().equals(password)){
                            SucUser=user;
                            count++;
                        }
                    }
                }

                if(count>0){
                    //Intent intent = new Intent(getActivity(),MainActivity.class);
                    Intent intent = new Intent(getActivity(),ListActivity.class);
                    intent.putExtra("Mail",email);
                    intent.putExtra("Pwd",password);
                    intent.putExtra("Fname",SucUser.getFname());
                    intent.putExtra("Lname",SucUser.getLname());

                    startActivity(intent);
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }


            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackFragment!=null){
                    callBackFragment.changeFragment();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO OPEN NEW FRAGMENT FOR SECURITY QUESTIONS
                Toast.makeText(getActivity(), "BHUL GAYA NA", Toast.LENGTH_SHORT).show();
                    callBackFragment.goToForgetFragment();

            }
        });

        return view;
    }

    public void setCallBackFragment(CallBackFragment callBackFragment){
        this.callBackFragment=callBackFragment;
    }
}
