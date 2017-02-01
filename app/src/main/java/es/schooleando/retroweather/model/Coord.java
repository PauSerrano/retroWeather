
package es.schooleando.retroweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coord implements Serializable {

    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("lat")
    @Expose
    public Double lat;

}
