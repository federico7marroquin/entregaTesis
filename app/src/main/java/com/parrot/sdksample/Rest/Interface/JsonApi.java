package com.parrot.sdksample.Rest.Interface;


import java.util.List;

import com.parrot.sdksample.Rest.Model.Posts;
import com.parrot.sdksample.entidades.Transformacion;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi {

    @GET("m2m/applications/aplicacion/containers/cont1/contentInstances/latest/content")
    Call<Posts> getPost();

    @GET("/posts")
    Call<List<Transformacion>> getPersonajes();
}
