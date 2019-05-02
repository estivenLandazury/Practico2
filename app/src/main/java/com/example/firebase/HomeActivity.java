package com.example.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView user;
    private RecyclerView listaMateria;
    private AdapterTemplate adapter;
    FirebaseAuth maAuth;
    FirebaseDatabase database;
    List<Materia> materias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user=findViewById(R.id.tv_user);
        Bundle bundle= getIntent().getExtras();
        materias=new ArrayList<>();

        //inicialización Firebase
        maAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        user.setText("hola "+bundle.getString("user"));

        listaMateria=findViewById(R.id.lista_materias);
        adapter= new AdapterTemplate();
        listaMateria.setHasFixedSize(true);
        listaMateria.setLayoutManager(new LinearLayoutManager(this));
        listaMateria.setHasFixedSize(true);
        listaMateria.setAdapter(adapter);

///Aqui Agrego a la base de datos
//        Materia n= new Materia("Seguridad", "4", "5");
//        database.getReference().child("Materia").child(maAuth.getCurrentUser().getUid()).push().setValue(n);


        //aqui recorre la lista de usuarios de firebase
        database.getReference().child("Materia").child(maAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot hijo: dataSnapshot.getChildren()){
                    Materia m= hijo.getValue(Materia.class);
                    adapter.agregarMateria(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
