package com.jiuwu.picboutique.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwu.picboutique.R;
import com.jiuwu.picboutique.bean.RssInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Terry on 2015/4/18.
 */

@EViewGroup(R.layout.item_pic_view)
public class Item_List_View extends LinearLayout {

    @ViewById
    ImageView centerImage;

    @ViewById
    TextView text;

    public Item_List_View(Context context) {
        super(context);
    }

    public Item_List_View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Item_List_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews(RssInfo info){
        text.setText(info.title);
        ImageLoader.getInstance().displayImage(
                info.list.get(0),
                centerImage,
                new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build());
    }



}


