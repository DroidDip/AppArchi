package com.droiddip.apparchi.data.network.retrofit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Dipanjan Chakraborty on 17-01-2018.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "responsecode",
        "responsedetails"
})
public class BaseResponse {

    @JsonProperty("responsecode")
    private String responsecode;
    @JsonProperty("responsedetails")
    private String responsedetails;

    @JsonProperty("responsecode")
    public String getResponsecode() {
        return responsecode;
    }

    @JsonProperty("responsecode")
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    @JsonProperty("responsedetails")
    public String getResponsedetails() {
        return responsedetails;
    }

    @JsonProperty("responsedetails")
    public void setResponsedetails(String responsedetails) {
        this.responsedetails = responsedetails;
    }

}
