package es.schooleando.retroweather.Presenter;

import android.util.Log;

import java.util.List;

import es.schooleando.retroweather.Service.WeatherService;
import es.schooleando.retroweather.model.Weather;
import es.schooleando.retroweather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruben on 23/01/17.
 */

public class WeatherPresenter {
    private final WeatherPresenterListener mListener;
    private final WeatherService weatherService;

    public interface WeatherPresenterListener{
        void weatherReady(WeatherModel weather);
        void weatherError(String error);
    }

    public WeatherPresenter(WeatherPresenterListener listener){
        this.mListener = listener;
        this.weatherService = new WeatherService();
    }

    public void getWeather(String codigo) {
        // Aquí llamamos al método de countryService, encolamos y implementamos el callback
        // También deberemos llamar a la Activity a traves del listener (WeatherPresenterListerer)

        // TODO: obtener weatherModel a través del weatherService
        String key = "92cf9f984f7f339d2734888afabf6302";

        //Obtenemos el call con la Api creada en WeatherService
        Call<WeatherModel> callWeatherModel = weatherService.getAPI().getWeatherConditions(codigo,key);
        //Lo encolamos en el Loop del ¿¿¿ UI ???
        callWeatherModel.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                //Recogemos el WeatherModel cuando se ejecute en el Loop, ya que llamara a este metodo
                WeatherModel tiempoModelo = response.body();
                //Lo indicamos por el log

                //Si va bien, pasamos al main activity el tiempoModelo para tener alli toda la informacion que necesitemos
                // a través del listener, donde implementaremos el metodo en el activity Main
                if (tiempoModelo!=null){
                    Log.i("CALLBACK-ONRESPONSE", response.raw().toString() );
                    Log.i("CALLBACK-ONRESPONSE", response.body().toString() );
                    //Comprobamos que podemos acceder a la lista de weather del tiempomodel
                    List<Weather> listaTiempo = response.body().weather;
                    //Lo indicamos por el Log el primer tempo de la lista
                    Log.i("CALLBACK-ONRESPONSE", listaTiempo.get(0).description);
                    mListener.weatherReady(tiempoModelo);
                } else {
                    //Por lo contrario, si obtnenemos un error, lo pasaremos tambien al activity implementando alli este otro metodo
                    String errorString = "Codigo error: " + response.code();
                    mListener.weatherError(errorString);
                    Log.i("CALLBACK-ONRESPONSE", errorString);
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

                //Si falla la respuesta del get pasamos el mensage de error tambien al mainActivity

                if (t !=null){

                    String errorString = "Error. Mensaje: " + t.getMessage();
                     mListener.weatherError(errorString);
                }

                Log.i("CALLBACK-ONFAILURE", "Se ha producido algun error");
            }
        });

    }
}
