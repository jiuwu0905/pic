package com.jiuwu.picboutique;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.jiuwu.picboutique.view.HackyViewPager;
import com.jiuwu.picboutique.view.ZoomOutPageTransformer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

@EActivity(R.layout.activity_showimg)
public class ShowLargePictureActivity extends Activity {

//	private static String[] imgPath;
	private ArrayList<String> imgPath;
	private int currentPictruePosition = 0;

	private boolean isAddPic = false;

	private DisplayImageOptions options;

	private ArrayList<String> deleteImages = null;

	@ViewById
	HackyViewPager view_pager;

	@ViewById
	TextView content;


	@AfterViews
	void afterView() {

		initLoadingImage();
		getintents();
		initTopView();
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		view_pager.setAdapter(new SamplePagerAdapter());
		view_pager.setPageTransformer(true, new ZoomOutPageTransformer());
		view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				content.setText(getString(R.string.pic_title_number,
						(arg0 + 1), imgPath.size()));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		view_pager.setCurrentItem(currentPictruePosition);
		if (currentPictruePosition == 0) {
			content.setText(getString(R.string.pic_title_number,
					(currentPictruePosition + 1), imgPath.size()));
		}
	}

	private void getintents() {
		// TODO Auto-generated method stub

//		imgPath = getIntent().getStringArrayExtra("imgPath");
		imgPath = getIntent().getStringArrayListExtra("imgPath");
		currentPictruePosition = getIntent().getIntExtra("position", 0);
	}

	private void initTopView() {
		// TODO Auto-generated method stub
	}

	private void initLoadingImage() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.resetViewBeforeLoading().cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300))// 图片渐现的时间
				.build();
	}

	class SamplePagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return imgPath.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			// photoView.setImageResource(sDrawables[position]);

			ImageLoader.getInstance().displayImage(imgPath.get(position),
					photoView, options);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}




}
