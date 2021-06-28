package com.example.drawer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drawer.R;
import com.example.drawer.databinding.FragmentAuthBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private String phone;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(getLayoutInflater(), container, false);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initCallback();
    }

    private void initCallback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("TAG", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d("TAG", "onCodeSent:" + verificationId);

                mVerificationId = verificationId;
            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                       NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                       navController.navigate(R.id.nav_home);
                        // Update UI
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }

    private void init() {
        binding.btnGetCode.setOnClickListener(v -> {
            privatePhone();

            binding.btnGetCode.setVisibility(View.GONE);
            binding.btnContinio.setVisibility(View.VISIBLE);
        });
        binding.btnContinio.setOnClickListener(v -> {
            checkCodeSms();
        });
    }

    private void checkCodeSms() {
        String smsCode =  binding.etCode.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode) || smsCode.length() > 6){
            binding.etCode.setError("Input sms correct code!");
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, smsCode);
        signInWithPhoneAuthCredential(credential);

    }

    private void privatePhone() {
         phone = "+996" + binding.etNumber.getText().toString().trim();
    if (TextUtils.isEmpty(phone) && phone.length() > 8){
        binding.etNumber.setError("Input number correct!");
        return;
    }
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}