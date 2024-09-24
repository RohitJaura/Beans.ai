package com.beans.ai;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.beans.ai.databinding.ActivityNativeBinding;

public class NativeActivity extends AppCompatActivity {

    ActivityNativeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNativeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String firstName = getIntent().getStringExtra("firstname");
        String lastName = getIntent().getStringExtra("lastname");
        String gender = getIntent().getStringExtra("gender");
        String employeeId = getIntent().getStringExtra("empid");
        String designation = getIntent().getStringExtra("designation");
        String phoneNumber = getIntent().getStringExtra("phno");
        String result = "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Gender: " + gender + "\n" +
                "Employee ID: " + employeeId + "\n" +
                "Designation: " + designation + "\n" +
                "Phone Number: " + phoneNumber;

        binding.tv.setText(result);

        //closure
    }
}