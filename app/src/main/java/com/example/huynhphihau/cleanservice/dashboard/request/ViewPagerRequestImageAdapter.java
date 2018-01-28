package com.example.huynhphihau.cleanservice.dashboard.request;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.huynhphihau.cleanservice.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by huynhphihau on 1/23/18.
 */

public class ViewPagerRequestImageAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> images = new ArrayList<>();
    LayoutInflater inflater;
    /**
     * listener
     */
    ViewImageListener mListener;


    public ViewPagerRequestImageAdapter(Context context, ViewImageListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variable
        ImageView imgReports;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_image_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        imgReports = itemView.findViewById(R.id.img_report);

        // Capture position and set to the ImageView
        File imgFile = new File(images.get(position));
        if (imgFile.exists()) {
            Picasso.with(this.context)
                    .load(imgFile)
                    .placeholder(R.drawable.no_image)
                    .resize(100, 100)
                    .centerCrop()
                    .error(R.drawable.no_image)
                    .into(imgReports);
        }


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != PagerAdapter.POSITION_NONE) {
                    mListener.onViewImage(images.get(position));
                }
            }
        });

        // Add viewpager_item.xml to ViewPager
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    /*
    * Type: 0 is file path (camera), 1 is GALLERY
    *
    * */
    public void setImages(String image) {
        this.images.add(image);
        notifyDataSetChanged();
    }

    /**
     * listener for  Image adapter
     */
    public interface ViewImageListener {
        void onViewImage(String imagePath);
    }
}
