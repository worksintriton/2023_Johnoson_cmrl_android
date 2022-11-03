package com.triton.johnson.alerter;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import com.triton.johnson.R;
import com.triton.johnson.api.ApiCall;
import com.triton.johnson.responsepojo.ViewTicketsResponse;

import java.util.List;


public class ViewPagerTicketPhotoDetailsAdapter extends PagerAdapter {
    private ViewTicketsResponse.DataBean.TicketPhotoBean currentItem;
    List<ViewTicketsResponse.DataBean.TicketPhotoBean> ticketphotolist;
    private Context context;
    private LayoutInflater inflater;
    private View itemView;

    private String TAG = "ViewPagerTicketPhotoDetailsAdapter";

    public ViewPagerTicketPhotoDetailsAdapter(Context context, List<ViewTicketsResponse.DataBean.TicketPhotoBean> ticketphotolist){

        this.context = context;
        this.ticketphotolist = ticketphotolist;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ticketphotolist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View itemView = inflater.inflate(R.layout.sliding_image, view, false);
        ImageView imageView = itemView.findViewById(R.id.itemImage);


        try {
            String imageURL = ApiCall.API_URL+ticketphotolist.get(position).getImage_path();
            Log.w(TAG, "imageURL : " + imageURL);
            if(imageURL != null && !imageURL.isEmpty()){
                Glide.with(context)
                        .load(imageURL)
                        .into(imageView);
            }/*else{
                Glide.with(context)
                        .load(APIClient.BANNER_IMAGE_URL)
                        .into(imageView);

            }
*/

        } catch (Exception e) {
            // Handle the condition when str is not a number.
        }






        view.addView(itemView);

        return itemView;

    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
