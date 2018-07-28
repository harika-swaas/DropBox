package info.API.Service;

import info.API.Model.ApiResponse;
import info.API.Model.DropBoxContentModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by harika on 25-07-2018.
 */

public interface DropBoxContentService {

    String BASE_URL = "https://dl.dropboxusercontent.com";

    @GET("/s/2iodh4vg0eortkl/facts.json")
    Call<ApiResponse<DropBoxContentModel>> getDropBoxContent();
}
