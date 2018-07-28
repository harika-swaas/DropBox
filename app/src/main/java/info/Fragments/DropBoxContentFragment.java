package info.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.API.Model.ApiResponse;
import info.API.Model.DropBoxContentModel;
import info.API.Service.DropBoxContentService;
import info.Adapter.DropBoxContentAdapter;
import info.Common.DividerItemDecoration;
import info.Network.NetworkUtils;
import info.Retrofit.RetrofitAPIBuilder;
import info.dropbox.DropBoxContentActivity;
import info.dropbox.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harika on 28-07-2018.
 */

public class DropBoxContentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    DropBoxContentActivity mActivity;
    View mView;

    @BindView(R.id.dropbox_content_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    DropBoxContentAdapter mAdapter;

    String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (DropBoxContentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dropbox_content_fragment, container, false);
        ButterKnife.bind(this, mView);

        // SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getDropBoxContent();

                if(!TextUtils.isEmpty(title)) {
                    // Set title bar
                    ((DropBoxContentActivity) getActivity()).setActionBarTitle(title);
                } else {
                    ((DropBoxContentActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title));
                }
            }
        });

        return mView;
    }

    public void onResume(){
        super.onResume();

        // Fetching data from server
        getDropBoxContent();

        if(!TextUtils.isEmpty(title)) {
            // Set title bar
            ((DropBoxContentActivity) getActivity()).setActionBarTitle(title);
        } else {
            ((DropBoxContentActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title));
        }
    }

    private void getDropBoxContent() {

        if (NetworkUtils.isNetworkAvailable(mActivity)) {

            // Showing refresh animation before making http call
            mSwipeRefreshLayout.setRefreshing(true);

            Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();

            DropBoxContentService dropBoxContentService = retrofitAPI.create(DropBoxContentService.class);
            Call call = dropBoxContentService.getDropBoxContent();

            call.enqueue(new Callback<ApiResponse<DropBoxContentModel>>() {
                @Override
                public void onResponse(Call<ApiResponse<DropBoxContentModel>> call, Response<ApiResponse<DropBoxContentModel>> response) {
                    ApiResponse apiResponse = response.body();
                    if(apiResponse != null){

                        title = response.body().getTitle();

                        List<DropBoxContentModel> dropBoxContentModelList = new ArrayList<DropBoxContentModel>();
                        dropBoxContentModelList = response.body().getRows();

                        //pass content list to adapter to bind it to views
                        setAdapterToView(dropBoxContentModelList);
                    }

                    // Stopping swipe refresh
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<ApiResponse<DropBoxContentModel>> call, Throwable t) {
                    // Stopping swipe refresh
                    mSwipeRefreshLayout.setRefreshing(false);

                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setAdapterToView(List<DropBoxContentModel> dropBoxContentModelList) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, null));
        mAdapter = new DropBoxContentAdapter(dropBoxContentModelList, mActivity);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {

        getDropBoxContent();

        if(!TextUtils.isEmpty(title)) {
            // Set title bar
            ((DropBoxContentActivity) getActivity()).setActionBarTitle(title);
        } else {
            ((DropBoxContentActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title));
        }
    }

}
