package com.jiuwu.picboutique.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.MyReqest.XMLRequest;
import com.android.volley.Response;
import com.jiuwu.picboutique.MyApp_;
import com.jiuwu.picboutique.R;
import com.jiuwu.picboutique.ShowLargePictureActivity_;
import com.jiuwu.picboutique.adapter.ListAdapter;
import com.jiuwu.picboutique.bean.RssInfo;
import com.jiuwu.picboutique.tools.L;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable,AdapterView.OnItemClickListener {
    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    public static final String BOOK = "Book";
    public static final String PAINT = "Paint";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;

    private ListView myListView;

    private ListAdapter mAdapter;

    private ArrayList<RssInfo> list = new ArrayList<RssInfo>();

    private String[] text = {"http://wanimal.lofter.com/rss",
                            "http://9mouth.lofter.com/rss",
                            "http://dashu-fk.lofter.com/rss",
                            "http://goofan.lofter.com/rss",
                            "http://gun-rose.lofter.com/rss",
                            "http://beautypasse.lofter.com/rss"
    };

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
        mAdapter = new ListAdapter(getActivity(),null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        myListView = (ListView)rootView.findViewById(R.id.listView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                mAdapter);
        swingBottomInAnimationAdapter.setAbsListView(myListView);
        myListView.setAdapter(swingBottomInAnimationAdapter);
        //myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(this);
        getData();

    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }


    public void getData(){
        String url = text[res];
        L.d(url);
        XMLRequest xmlRequest  = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                // TODO Auto-generated method stub
                parseXml(response);
            }
        }, null);
        MyApp_.getInstance().mRequestQueue.add(xmlRequest);
    }

    private void parseXml(XmlPullParser response){
        try {
            RssInfo info = null;
            int eventType = response.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG) {
                    String nodeName = response.getName();
                    if ("channel".equals(nodeName)) {
                        list = new ArrayList<RssInfo>();
                    }else if("title".equals(nodeName)){
                        info = new RssInfo();
                        info.title = response.nextText();
                    }else if("description".equals(nodeName)){
                        info.description = response.nextText();
                    }

                }else if (eventType == XmlPullParser.END_TAG){
                    String nodeName = response.getName();
                    if("item".equals(nodeName)) {
                        if(info.description.contains("<img")) {
                            info.list = getImageList(info.description);
                            list.add(info);
                        }
                        info = null;
                    }
                }

                eventType = response.next();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        mAdapter.upData(list, true);

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                L.d(list.get(i).title);
                L.d("size------>"+list.get(i).list.size());
            }
        }

    }


    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    private ArrayList<String> getImageList(String str) {


        ArrayList<String> arrString = new ArrayList<String>();
        Pattern p = Pattern.compile("<img.*?>");
        Matcher m = p.matcher(str);
        while(m.find()) {
            arrString.add(str.substring(m.start(),m.end()).replaceAll("<img.*?src=\"", "").replaceAll("\" />",""));
        }
        return arrString;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RssInfo info = (RssInfo)adapterView.getItemAtPosition(i);
        startActivity(new Intent(getActivity(), ShowLargePictureActivity_.class).putStringArrayListExtra("imgPath", info.list));
    }
}

