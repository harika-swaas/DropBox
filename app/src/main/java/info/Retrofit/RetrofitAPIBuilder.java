package info.Retrofit;

import info.API.Service.DropBoxContentService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIBuilder {

    static Retrofit retrofit = null;

    public static synchronized Retrofit getInstance() {

        if (retrofit == null) {
            //Creating a retrofit object
            retrofit = new Retrofit.Builder()
                    .baseUrl(DropBoxContentService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())  //we are using the GsonConverterFactory to directly convert json data to object
                    .build();
        }
        return retrofit;
    }
}
