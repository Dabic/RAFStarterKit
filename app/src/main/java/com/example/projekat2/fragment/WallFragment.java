package com.example.projekat2.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.adapter.MessageAdapter;
import com.example.projekat2.model.Message;
import com.example.projekat2.model.User;
import com.example.projekat2.viewModel.ChatViewModel;
import com.example.projekat2.viewModel.WallViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WallFragment extends Fragment {

    public static WallFragment newInstance() {
        return new WallFragment();
    }
    private ImageButton send;
    private ImageButton photo;
    private EditText poruka;
    private WallViewModel wallViewModel;
    private MessageAdapter messageAdapter;
    private static final int REQUEST_CAMERA_PERMISSION = 222;
    private static final int REQUEST_CAMERA_PHOTO = 333;
    private File mPhotoFile;
    private StorageReference storageReference;
    private User sender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wall_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void init(View view){
        wallViewModel = ViewModelProviders.of(this).get(WallViewModel.class);
        wallViewModel.getMessageLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messageAdapter.setData(messages);
            }
        });
        messageAdapter = new MessageAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.wall_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageAdapter);

        send = view.findViewById(R.id.wall_send_btn);
        photo = view.findViewById(R.id.wall_photo_btn);
        poruka = view.findViewById(R.id.wall_text);
        sender = wallViewModel.getLoggedInUser();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message newMessage = new Message();
                String content = poruka.getText().toString();
                DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                DateFormat df2 = new SimpleDateFormat("h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                String time = df2.format(Calendar.getInstance().getTime());

                newMessage.setText(content);
                newMessage.setSender(sender);
                newMessage.setTime(date+" "+time);
                newMessage.setType(3);

                if (content != "") {
                    wallViewModel.addMessage(newMessage);
                }
                poruka.setText("");
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("caooooos");
                takePhoto();
            }
        });
        initFirebase();
    }
    private void initFirebase(){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("posted/");
    }
    @SuppressLint("MissingPermission")
    private void takePhoto() {


        if (!hasAnyFeature(PackageManager.FEATURE_CAMERA)) {
            return;
        }

        if (hasPermissions(Manifest.permission.CAMERA)) {


            try {
                mPhotoFile = createImageFile();


            } catch (IOException e) {
                e.printStackTrace();

            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Uri photoURI = FileProvider.getUriForFile(getActivity(), getString(R.string.app_file_provider),
                    mPhotoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(intent, REQUEST_CAMERA_PHOTO);
        } else {

            requestPermissions(REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode != REQUEST_CAMERA_PHOTO ) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }


        Uri uri = Uri.fromFile(mPhotoFile);

        StorageReference photoReference = storageReference.child(mPhotoFile.getName());

        photoReference.putFile(uri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //long progress = ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        //mStatusTv.setText(progress + "% uploaded");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {


                return photoReference.getDownloadUrl();
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                User uploader = wallViewModel.getLoggedInUser();
                DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                DateFormat df2 = new SimpleDateFormat("h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                String time = df2.format(Calendar.getInstance().getTime());

                Message message = new Message(uri.toString());
                message.setType(2);
                message.setTime(date+" "+time);
                message.setSender(uploader);
                wallViewModel.addMessage(message);
            }
        });
    }



    protected boolean hasAnyFeature(String... features){
        for (String feature : features) {
            if (getActivity().getPackageManager().hasSystemFeature(feature)){
                return true;
            }
        }
        return false;
    }

    protected boolean hasPermissions(String... permissions){
        for (String permission : permissions) {
            boolean hasPermission = ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
            if(!hasPermission) {
                return false;
            }
        }
        return true;
    }

    protected void requestPermissions(int requestCode, String... permissions){
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
}
