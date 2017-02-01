
package es.schooleando.retroweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rain implements Serializable {

    @SerializedName("3h")
    @Expose
    public Double _3h;

}
