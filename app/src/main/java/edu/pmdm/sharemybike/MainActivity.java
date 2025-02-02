package edu.pmdm.sharemybike;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import edu.pmdm.sharemybike.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //La posicion de la dirección postal
    private String longitud = "-4.836934759600515";
    private String latitud = "39.95575197869839";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obtenemos un inflater
        LayoutInflater inflater = getLayoutInflater();
        //Obtenemos la jerarquia de vistas pasando el inflater como argumento
        //a la clase estática ActivityMainBinding
        binding = ActivityMainBinding.inflate(inflater);

        //Obtenemos el elemento raíz del layout
        ViewGroup root = binding.getRoot();
        mostrarNombre(root);

        //Establecemos el contenido de la vista
        setContentView(root);

        setListeners(this);
    }

    private void setListeners(Context context) {
        MainActivity activity = (MainActivity) context;

        //Boton de localizacion
        ImageButton ibLocation = binding.ibLocation;
        ibLocation.setOnClickListener(activity);

        //Boton de email
        ImageButton ibEmail = binding.ibEmail;
        ibEmail.setOnClickListener(activity);

        //Boton de login
        Button btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(activity);
    }

    @Override
    public void onClick(View view) {

        //ImageButton de localización
        if (view.getId() == R.id.ibLocation) {
            mostrarUbicacion();
        //ImageButton de email
        } else if (view.getId() == R.id.ibEmail) {
            mostrarEmail();
        //Iniciar la actividad BikeActivity
        } else if (view.getId() == R.id.btnLogin){
            Intent intent = new Intent(this, BikeActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Método para enviar un email
     */
    private void mostrarEmail() {
        Intent intent = new Intent();
        Intent chooser = null;

        intent.setAction(Intent.ACTION_SEND);

        String sEmail = binding.tvEmail.getText().toString();
        String uriEmail = "mailto:";
        intent.setData(Uri.parse(uriEmail));

        String[] destinos = {sEmail, "admin.sharemybike@gmail.com"};

        //3. Extras
        intent.putExtra(Intent.EXTRA_EMAIL, destinos);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Saludos de ShareMyBike");
        intent.putExtra(Intent.EXTRA_TEXT, "¡Hola, qué tal estás!");

        intent.setType("message/rfc822");
        chooser = intent.createChooser(intent, "Enviar Email");

        startActivity(chooser);
    }

    /**
     * Método para mostrar ubicación de la dirección postal
     */
    private void mostrarUbicacion() {
        Intent intent = new Intent();
        //1.Acción: ver el contenido
        intent.setAction(Intent.ACTION_VIEW);
        //2. Data: posicion geografica
        String geoUri = "geo:" + latitud + "," + longitud;
        intent.setData(Uri.parse(geoUri));

        // Especificar el paquete de Google Maps
        intent.setPackage("com.google.android.apps.maps");

        // Verificar si Google Maps está instalado
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Sugerir instalar Google Maps
            Intent playIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.google.android.apps.maps"));
            startActivity(playIntent);
        }
    }


    private void mostrarNombre(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup myViewGroup = (ViewGroup) view;
            int viewId = myViewGroup.getId();

            if (viewId != View.NO_ID) { // Verifica si la vista tiene un id asignado
                String viewName = getBaseContext().getResources().getResourceEntryName(viewId);
                Log.e("AdaptadorBaseAdapter", "getView: ViewGroup ID = " + viewId +
                        ", Name = " + viewName +
                        ", Class Name = " + myViewGroup.getClass().getSimpleName());
            } else {
                Log.e("AdaptadorBaseAdapter", "getView: ViewGroup has no ID assigned.");
            }
        }
    }
}