package es.schooleando.retroweather;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;

import es.schooleando.retroweather.Presenter.WeatherPresenter;
import es.schooleando.retroweather.model.WeatherModel;

public class MainActivity extends AppCompatActivity implements WeatherPresenter.WeatherPresenterListener{

    // DATOS necesarios ++Retrofit++
    WeatherPresenter weatherPresenter;
    String codigo;

    // Parte GRAFICA
    ListView lista;
    ArrayAdapter<String> adaptador;
    String[] ciudadesArray = {"Valencia", "Madrid", "Barcelona", "Londres", "Bilbao"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listaCiudades);
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ciudadesArray);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String ciudad = adaptador.getItem(position);
                codigo = null;

                switch (ciudad){
                    case "Valencia":
                        codigo = "valencia,es";
                        break;
                    case "Barcelona":
                        codigo = "barcelona,es";
                        break;
                    case "Madrid":
                        codigo = "madrid,es";
                        break;
                    case "Londres":
                        codigo = "london,uk";
                        break;
                    case  "Bilbao":
                        codigo = "bilbao,es";
                        break;

                }
                ejecutarGetWeather(codigo);
            }
        });




    }

    // Al ejecutar el getWeather, se produciran las llamadas a estos metodos, que nos proporcionan:
        //O un WheatherModel
    @Override
    public void weatherReady(WeatherModel weather) {
        // aquí obtenemos las actualizaciones gracias a WeatherPresenter y abrimos una nueva Actividad
        //donde pasamos el weather que hemos obtenido.
        Intent intentDatos = new Intent(this, DatosTiempoActivity.class);
        intentDatos.putExtra("tiempoModelo", weather);
        intentDatos.putExtra("codigoCiudad", codigo);
        startActivity(intentDatos);


    }

        //O un mensaje de error
    @Override
    public void weatherError(String error) {

        //Si el estring que nos llega no es null, es que se ha producido un error, por lo que
        //indicamos el texto en un toast

        if(error!=null){

            Toast.makeText(this,error, Toast.LENGTH_LONG).show();
        } else {
            //Si el null es que ha ido bien, por lo que no necesitamos indicar nada
        }
    }

    public void ejecutarGetWeather(String codigoCiudad){

                //instanciamos la clase creada para realizar la conexion con la web del tiempo
                //El this se refiere a que esta clase es un listener ya que implementa a WeatherPresenter.WeatherPresenterListener
                weatherPresenter = new WeatherPresenter(this);
                //Ejecutamo el metodo get que hemos creado en la clase que ejecutará  el servicio que hemos creado
                //y los metodos onresponse y onFailture
                weatherPresenter.getWeather(codigoCiudad);

    }
}
