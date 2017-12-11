package com.rootminusone.spacestationpasses;

import com.rootminusone.spacestationpasses.model.PassesResponse;
import com.rootminusone.spacestationpasses.model.Response;
import com.rootminusone.spacestationpasses.network.PassesApi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gangares on 12/10/17.
 */

public class PassesPresenter implements StationPassesContractHub.Presenter {

    private Retrofit retrofit;
    private static final String BASE_URL ="http://api.open-notify.org/";
    private WeakReference<StationPassesContractHub.View> viewWeakReference;

    public PassesPresenter(StationPassesContractHub.View view) {
        viewWeakReference = new WeakReference<>(view);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public void loadPasses(final Map<String, String> queryParams) {
        if (retrofit == null) {
            retrofit = getRetrofit();
        }
        PassesApi passesApi = retrofit.create(PassesApi.class);
        passesApi.getPasses(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PassesResponse>() {
                    @Override
                    public void accept(PassesResponse passesResponse) throws Exception {
                        handleResponse(passesResponse);
                    }
                });
    }

    private void handleResponse(final PassesResponse passesResponse) {
        if (passesResponse != null && passesResponse.getResponse() != null) {
            ArrayList<Response> response = passesResponse.getResponse();
            if (!response.isEmpty() && viewWeakReference.get() != null) {
                viewWeakReference.get().displayPasses(response);
            }
        }
    }
}


