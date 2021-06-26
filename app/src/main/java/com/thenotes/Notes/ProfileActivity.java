package com.thenotes.Notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.thenotes.Notes.paketku.AdapterDataku;
import com.thenotes.Notes.paketku.Dataku;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    FloatingActionButton tblData;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Data");
    List<Dataku> list = new ArrayList<>();
    AdapterDataku adapterDataku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       tblData = findViewById(R.id.tbl_data);
       recyclerView= findViewById(R.id.resikel);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       tblData.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialogTambahData();
           }
       });
       bacaData();
    }

    private void bacaData() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Dataku value = snapshot.getValue(Dataku.class);
                    list.add(value);
                }
                adapterDataku = new AdapterDataku(ProfileActivity.this,list);
              recyclerView.setAdapter(adapterDataku);

              setClick();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void setClick() {
        adapterDataku.setOnCallBack(new AdapterDataku.OnCallBack() {
            @Override
            public void onTblHapus(Dataku dataku) {
                hapusData(dataku);
            }

            @Override
            public void onTblEdit(Dataku dataku) {
                showDialogEditData(dataku);

            }
        });
    }

    private void showDialogEditData(Dataku dataku) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp =new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_keluar);
        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText txtTambah = dialog.findViewById(R.id.txt_tambah);
        Button tblTambah = dialog.findViewById(R.id.tbl_tambah);
        TextView tvTambah = dialog.findViewById(R.id.tv_tambah);
        txtTambah.setText(dataku.getIsi());
        tblTambah.setText("Update");
        tvTambah.setText("Edit data");

        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtTambah.getText())) {
                    tblTambah.setError("Silahkan isi data");
                } else {
                   editData(dataku, txtTambah.getText().toString());
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    private void editData(Dataku dataku, String baru) {
        myRef.child(dataku.getKunci()).child("isi").setValue(baru).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Update Berhasil",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hapusData(Dataku dataku) {
        myRef.child(dataku.getKunci()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(),dataku.getIsi()+"telah dihapus",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showDialogTambahData() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp =new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_keluar);
        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText txtTambah = dialog.findViewById(R.id.txt_tambah);
        Button tblTambah = dialog.findViewById(R.id.tbl_tambah);
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtTambah.getText())) {
                    tblTambah.setError("Silahkan isi data");
                } else {
                    simpanData(txtTambah.getText().toString());
                    dialog.dismiss();
                }

            }
        });
        dialog.show();

    }

    private void simpanData(String s) {
        // Write a message to the database

        String kunci = myRef.push().getKey();
        Dataku dataku = new Dataku(kunci,s);

        myRef.child(kunci).setValue(dataku).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

