package com.rootminusone.spacestationpasses.network;

import com.rootminusone.spacestationpasses.model.PassesResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by gangares on 12/10/17.
 */

public interface PassesApi {

    @GET("/iss-pass.json")
    Observable<PassesResponse> getPasses(@QueryMap Map<String, String> params);
}
