package es.schooleando.retroweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.schooleando.retroweather.Presenter.WeatherPresenter;
import es.schooleando.retroweather.model.WeatherModel;

public class DatosTiempoActivity extends AppCompatActivity implements WeatherPresenter.WeatherPresenterListener{


    TextView ciudad;
    TextView condicion;
    TextView humedad;
    TextView temperatura;
    TextView viento;
    ImageView icono;


    WeatherPresenter weatherPresenter;
    WeatherModel tiempo;
    String codigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_tiempo);


        ciudad = (TextView) findViewById(R.id.tvNombreCiudad);
        condicion = (TextView) findViewById(R.id.tvCondicion);
        humedad = (TextView) findViewById(R.id.tvHumedad);
        temperatura = (TextView) findViewById(R.id.tvTemperatura);
        viento = (TextView) findViewById(R.id.tvViento);
        icono = (ImageView) findViewById(R.id.imageView);


        //Recuperamos el valor extra del intent si es null, si no es que se ha hecho click en actualizar
        if (tiempo == null){
            tiempo =  (WeatherModel) getIntent().getExtras().getSerializable("tiempoModelo");
            codigo = (String) getIntent().getExtras().getSerializable("codigoCiudad");
        }



        String ciudadString = tiempo.getName();
        String condicionString = tiempo.getWeather().get(0).getDescription();
        String humedadString = String.valueOf(tiempo.getMain().getHumidity()) ;
        String temperaturaString = String.valueOf(tiempo.getMain().getTemp());
        String vientoString = String.valueOf(tiempo.getWind().getSpeed()) ;
        String urlIcono = "http://openweathermap.org/img/w/"+tiempo.getWeather().get(0).getIcon()+".png";

        //Utilizamos picaso para descargar la imagen
        Picasso.with(this).load(urlIcono).into(icono);


        ciudad.setText(ciudadString);
        condicion.setText(condicionString);
        humedad.setText(humedadString);
        temperatura.setText(temperaturaString);
        viento.setText(vientoString);
    }




    public void actualizarTiempo(View view) {

        //Cuando Pinchamos a actualizar, cargamos otra vez los datos,
        //para ello, al estar en otra actividad, implimetamos aqui tambien el listener y
        //copiamos el codigo. Añadimos un if para coger el dato del weatherModel del intent o de aqui.

        ejecutarGetWeather(codigo);

    }


    @Override
    public void weatherReady(WeatherModel weather) {

        //Recogemos el nuevo WeatherModel
        tiempo = weather;
        //Actualizamos los campos
        String ciudadString = tiempo.getName();
        String condicionString = tiempo.getWeather().get(0).getDescription();
        String humedadString = String.valueOf(tiempo.getMain().getHumidity()) ;
        String temperaturaString = String.valueOf(tiempo.getMain().getTemp());
        String vientoString = String.valueOf(tiempo.getWind().getSpeed()) ;
        String urlIcono = "http://openweathermap.org/img/w/"+tiempo.getWeather().get(0).getIcon()+".png";

        //Utilizamos picaso para descargar la imagen
        Picasso.with(this).load(urlIcono).into(icono);


        ciudad.setText(ciudadString);
        condicion.setText(condicionString);
        humedad.setText(humedadString);
        temperatura.setText(temperaturaString);
        viento.setText(vientoString);
    }

    @Override
    public void weatherError(String error) {

        if(error!=null){

            Toast.makeText(this,error, Toast.LENGTH_LONG).show();
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
