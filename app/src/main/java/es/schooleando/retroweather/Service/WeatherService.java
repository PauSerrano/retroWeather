package es.schooleando.retroweather.Service;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.schooleando.retroweather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ruben on 23/01/17.
 */

public class WeatherService {
    private static final String BASE_URL = "httP://api.openweathermap.org/data/2.5/";




    public interface WeatherAPI {

        // TODO: Completar
        @GET ("weather")
        Call<WeatherModel> getWeatherConditions(@Query("q") String lugar,
                                                @Query("APPID") String key);


    }

    public WeatherAPI getAPI() {
        // TODO: creamos la instancia del interfaz del API mediante Retrofit
        // Retrofit retrofit ...
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit.create(WeatherAPI.class);
        // return retrofit.create(WeatherAPI.class);

    }
}
