
package es.schooleando.retroweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable {

    @SerializedName("message")
    @Expose
    public Double message;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("sunrise")
    @Expose
    public Integer sunrise;
    @SerializedName("sunset")
    @Expose
    public Integer sunset;

}
