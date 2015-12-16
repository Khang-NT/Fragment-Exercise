package com.hasbrain.test.fragment.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hasbrain.test.fragment.R;
import com.hasbrain.test.fragment.activity.RegisterActivity;
import com.hasbrain.test.fragment.model.FragmentCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Khang on 22/11/2015.
 */
public class Step1Fragment extends BaseFragment implements View.OnClickListener {
    public static final String FIRST_NAME = "FIRST_NAME", LAST_NAME = "LAST_NAME",
            EMAIL = "EMAIL", PHONE = "PHONE", SEX = "SEX";
    public static final java.lang.String PATH_AVATAR = "PATH_AVATAR";
    public static final int MALE = 0, FEMALE = 1;
    private static final String TAG = "Step1";

    private static final String folderSave = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .getAbsolutePath();
    public static final int CAMERA_REQUEST = 789;
    private static final int MAX_SIZE = 400;


    private EditText firstName, lastName, email, phone;
    private AppCompatImageView imageView;
    private RadioGroup sex;
    private Button next;

    public static boolean requestResetInput = false;

    public String pathAvatar = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step1_fragment, container, false);
        firstName = (EditText) v.findViewById(R.id.ed_fname);
        lastName = (EditText) v.findViewById(R.id.ed_lname);
        email = (EditText) v.findViewById(R.id.ed_email);
        phone = (EditText) v.findViewById(R.id.ed_phone);
        sex = (RadioGroup) v.findViewById(R.id.rg_sex);
        next = (Button) v.findViewById(R.id.b_next);
        imageView = (AppCompatImageView) v.findViewById(R.id.ivAvatar);
        if (savedInstanceState != null)
            pathAvatar = savedInstanceState.getString(PATH_AVATAR);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
        if (pathAvatar != null)
            imageView.setImageBitmap(BitmapFactory.decodeFile(pathAvatar));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (requestResetInput) {
            firstName.setText(null);
            lastName.setText(null);
            email.setText(null);
            phone.setText(null);
            imageView.setImageBitmap(null);

            requestResetInput = false;
        }
    }

    @Override
    protected Bundle saveState() {
        Log.e(TAG, "-------------------saveState----------------------");
        Bundle data = new Bundle();
        data.putString(FIRST_NAME, firstName.getText().toString());
        data.putString(LAST_NAME, lastName.getText().toString());
        data.putString(EMAIL, email.getText().toString());
        data.putString(PHONE, phone.getText().toString());
        data.putInt(SEX, sex.getCheckedRadioButtonId() == R.id.rb_female ?
                FEMALE : MALE);
        data.putString(PATH_AVATAR, pathAvatar);
        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_next:
                if (checkInput()) {
                    hideKeyboard();
                    Bundle data = saveState();
                    FragmentCallback callback = (FragmentCallback) getActivity();
                    callback.notifyEvent(RegisterActivity.STEP1
                            , data);
                }
                break;
        }
    }

    /**
     * Check input and warning error.
     *
     * @return return true if input is ok, else return false;
     */
    private boolean checkInput() {
        boolean isOk = true;

        if (pathAvatar == null) {
            Toast.makeText(getContext(), "You must choose an avatar", Toast.LENGTH_LONG).show();
            isOk = false;
        }

        if (isEmpty(firstName.getText())) {
            firstName.setError("Please enter your name");
            isOk = false;
        } else firstName.setError(null);

        if (isEmpty(lastName.getText())) {
            lastName.setError("Please enter your name");
            isOk = false;
        } else lastName.setError(null);

        if (!isValidEmail(email.getText())) {
            email.setError("Your email is invalid");
            isOk = false;
        } else email.setError(null);

        if (!isValidPhone(phone.getText())) {
            phone.setError("Your phone number is invalid");
            isOk = false;
        } else phone.setError(null);

        return isOk;
    }

    private boolean isValidEmail(CharSequence target) {
        return !isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPhone(CharSequence target) {
        final String regex = "^[+|0][0-9]{10,13}$";
        return !isEmpty(target) && target.toString().matches(regex);
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST) {
            Object o = data.getExtras().get("data");
            if (o != null) {
                Bitmap photo = (Bitmap) o;
                int dstWidth = max(max(photo.getWidth(), photo.getHeight()), MAX_SIZE);
                int dstHeight = (int) (((float) photo.getHeight() / photo.getWidth()) * dstWidth);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, dstWidth, dstHeight, false);

                //Save image captured to file
                FileOutputStream os = null;
                try {
                    File file = new File(folderSave, new Date().getTime() + ".jpg");
                    pathAvatar = file.getAbsolutePath();
                    os = new FileOutputStream(file);
                    byte[] allStream = bitmapToByteArr(scaledBitmap);
                    os.write(allStream, 0, allStream.length);
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (os != null)
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

                imageView.setImageBitmap(scaledBitmap);

                if (scaledBitmap != photo)
                    photo.recycle();
            }
        }
    }

    private byte[] bitmapToByteArr(Bitmap b) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }
}
