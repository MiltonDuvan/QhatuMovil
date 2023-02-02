package com.qhatusubasta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import Model.Producto;

public class RegistroProducto extends AppCompatActivity implements View.OnClickListener {
    Button btnpublicar;
    ImageButton img_producto;
    EditText edtDescripcionProducto, edtNombreProducto,edtFechainicioProducto,edtFechacierreProducto,edtHoracierreProducto, edtOferteProducto;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int año, mes, dia, hora, minutos;
    Uri uri;
    StorageReference storageReference;
    String t;

    private static final int Gallery_Code=1;
    Uri imageUrl=null;
    ProgressDialog progressDialog;

    // Barras de navegacion
    ImageButton imgbHome, imgbFavoritos, imgbPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegistroProducto.this, "mensaje para todos", Toast.LENGTH_SHORT).show();
            }
        });
        referenciar();
    }
    private void NotificacionProducto(){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
           // String token ="eV3ot7VjS7WqHg89aXOVIh:APA91bGTS8A0qYvEXH3p9KSTA92-eDP0nMDgkPjCmRyBADMBjgIS76h2cLJUwEG94G-tct2K3s5mXxqlgTJpheNW6e0As_eG-9FF3qL5ORI_D8FKe3nNxkEo7kNtzcq-ywmNyH1RxDN7";
            json.put("to", "/topics/" + "enviaratodos");
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Novedad nueva");
            notificacion.put("detalle", "Nuevo registro de Subasta");
            json.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key= BM3E07Jua8F_06wHsLd6Wbk_KZMVF36H2S7wf9SzxUt6fHlx_h0JmmYg6D6vEOaoluB8TXFrisOUKZbKF3Uz2Xk");
                    return header;
                }
            };

            myRequest.add(request);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void InsertarProducto(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Producto producto = new Producto();
        producto.setId(UUID.randomUUID().toString());
        producto.setFoto(String.valueOf(t));
        producto.setDescripcion(edtDescripcionProducto.getText().toString());
        producto.setNombre(edtNombreProducto.getText().toString());
        producto.setFecha_inicio(edtFechainicioProducto.getText().toString());
        producto.setFecha_cierre(edtFechacierreProducto.getText().toString());
        producto.setHora_cierre(edtHoracierreProducto.getText().toString());
        producto.setOferta_inicial(edtOferteProducto.getText().toString());
        myRef.child("Producto").child(producto.getId()).setValue(producto);

        Toast.makeText(this, "Datos insertados", Toast.LENGTH_SHORT).show();

    }
    private void referenciar() {
        img_producto = findViewById(R.id.img_subirencont);
        img_producto.setOnClickListener(this);
        edtDescripcionProducto = findViewById(R.id.edtDescripcionProducto);
        edtNombreProducto = findViewById(R.id.edtNombreProducto);
        edtFechainicioProducto = findViewById(R.id.edtFechainicioProducto);
        edtFechainicioProducto.setOnClickListener(this);
        edtFechacierreProducto = findViewById(R.id.edtFechacierreProducto);
        edtFechacierreProducto.setOnClickListener(this);
        edtHoracierreProducto = findViewById(R.id.edtHoracierreProducto);
        edtHoracierreProducto.setOnClickListener(this);
        edtOferteProducto = findViewById(R.id.edtOferteProducto);
        btnpublicar = findViewById(R.id.btnPublicarProducto);
        btnpublicar.setOnClickListener(this);

// Barras de navegacion
        imgbHome = findViewById(R.id.imgbHome);
        imgbHome.setOnClickListener(this);
        imgbFavoritos = findViewById(R.id.imgbFavoritos);
        imgbFavoritos.setOnClickListener(this);
        imgbPerfil = findViewById(R.id.imgbPerfil);
        imgbPerfil.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Code && resultCode == RESULT_OK){
            uri = data.getData();
            StorageReference filePath = storageReference.child("Fotos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                String imageUri=null;
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    img_producto.setImageURI(uri);

                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            t = task.getResult().toString();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPublicarProducto:
                InsertarProducto();
                NotificacionProducto();
                img_producto.setImageResource(0);
                edtDescripcionProducto.setText("");
                edtNombreProducto.setText("");
                edtFechainicioProducto.setText("");
                edtFechacierreProducto.setText("");
                edtHoracierreProducto.setText("");
                edtOferteProducto.setText("");
                break;
            case R.id.img_subirencont:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);

                break;

            case R.id.edtFechainicioProducto:
                if (v == edtFechainicioProducto){
                    final Calendar calendari = Calendar.getInstance();
                    dia = calendari.get(Calendar.DAY_OF_MONTH);
                    mes = calendari.get(Calendar.MONTH);
                    año = calendari.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroProducto.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            edtFechainicioProducto.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }
                            ,dia, mes, año);
                    datePickerDialog.show();
                }
                break;
            case R.id.edtFechacierreProducto:

                if (v == edtFechacierreProducto){
                    final Calendar calendarc = Calendar.getInstance();
                    dia = calendarc.get(Calendar.DAY_OF_MONTH);
                    mes = calendarc.get(Calendar.MONTH);
                    año = calendarc.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroProducto.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            edtFechacierreProducto.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }
                            ,dia, mes, año);
                    datePickerDialog.show();
                }
                break;
            case R.id.edtHoracierreProducto:
                if(v == edtHoracierreProducto){
                    final Calendar calendarc = Calendar.getInstance();
                    hora = calendarc.get(Calendar.HOUR_OF_DAY);
                    minutos = calendarc.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(RegistroProducto.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            edtHoracierreProducto.setText(hourOfDay + ":" + minute);
                        }
                    }
                    ,hora, minutos,false);
                    timePickerDialog.show();
                }
                break;

                // Barras de navegacion
                case R.id.imgbHome:
                    Intent home = new Intent(RegistroProducto.this,Index.class);
                    startActivity(home);
                    break;
                case R.id.imgbFavoritos:
                    Intent favoritos = new Intent(RegistroProducto.this,Favoritos.class);
                    startActivity(favoritos);
                    break;

                case R.id.imgbPerfil:
                    Intent perfil = new Intent(RegistroProducto.this,PerfilUser.class);
                    startActivity(perfil);
                    break;

        }
    }
}