package com.google.codelab.mlkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity implements CallBackFragment  {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        addFragment();
    }


    public void addFragment(){
        LoginFragment fragment=new LoginFragment();
        fragment.setCallBackFragment(this);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(){
        fragment=new RegisterFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();
    }
    public void replaceFragmentB(){
        fragment=new ForgotPasswordFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void changeFragment() {
        replaceFragment();
    }

    @Override
    public void goToForgetFragment() {
        replaceFragmentB();
    }

}