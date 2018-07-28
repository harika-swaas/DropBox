package info.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import info.API.Model.DropBoxContentModel;
import info.dropbox.R;


public class DropBoxContentAdapter extends RecyclerView.Adapter<DropBoxContentAdapter.ViewHolder> {

    final Context context;
    private List<DropBoxContentModel> mDropBoxContents;

    public DropBoxContentAdapter(List<DropBoxContentModel> mDropBoxContents, Activity context) {
        this.context = context;
        this.mDropBoxContents = mDropBoxContents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description;
        public ImageView image;
        public RelativeLayout parentLayout;

        public ViewHolder(View mView) {
            super(mView);
            title = (TextView) mView.findViewById(R.id.title);
            description = (TextView) mView.findViewById(R.id.description);
            image = (ImageView) mView.findViewById(R.id.image);
            parentLayout = (RelativeLayout) mView.findViewById(R.id.parent_layout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.drop_box_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mDropBoxContents != null && mDropBoxContents.size() > 0) {
            DropBoxContentModel dropBoxContentModel = mDropBoxContents.get(position);

            //Display title based on position to textview
            if(!TextUtils.isEmpty(dropBoxContentModel.getTitle())) {
                holder.title.setText(dropBoxContentModel.getTitle());
            }

            //Display description based on position to textview
            if(!TextUtils.isEmpty(dropBoxContentModel.getDescription())) {
                holder.description.setText(dropBoxContentModel.getDescription());
            }

            //DisplayImage from Ion
            if(dropBoxContentModel.getImageHref() != null) {
                Ion.with(context)
                        .load(dropBoxContentModel.getImageHref()).intoImageView(holder.image);
            }

            if(TextUtils.isEmpty(dropBoxContentModel.getDescription()) && TextUtils.isEmpty(dropBoxContentModel.getTitle()) && TextUtils.isEmpty(dropBoxContentModel.getImageHref())){
                holder.title.setText("Not available");
                holder.title.setTextSize(16.0f);
                holder.description.setVisibility(View.GONE);
                holder.image.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        //returns items size
        return mDropBoxContents.size();
    }
}
