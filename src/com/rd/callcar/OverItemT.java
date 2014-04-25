package com.rd.callcar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

class OverItemT extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	private Drawable marker;
	private StepOne mContext;
	private TextView textView1;
	private TextView textView2;

	public OverItemT(Drawable marker, Context context, double mLat,
			double mLon, String mer_name) {
		super(boundCenterBottom(marker));
		this.marker = marker;
		this.mContext = (StepOne) context;
		// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
		GeoPoint p1 = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
		// ����OverlayItem��������������Ϊ��item��λ�ã������ı�������Ƭ��
		mGeoList.add(new OverlayItem(p1, "", mer_name));
		populate(); // createItem(int)��������item��һ���������ݣ��ڵ�����������ǰ�����ȵ����������
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// Projection�ӿ�������Ļ��������;�γ������֮��ı任
		Projection projection = mapView.getProjection();
		for (int index = size() - 1; index >= 0; index--) { // ����mGeoList
			OverlayItem overLayItem = getItem(index); // �õ�����������item
			String title = overLayItem.getTitle();
			// �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ��������
			Point point = projection.toPixels(overLayItem.getPoint(), null);
			// ���ڴ˴�������Ļ��ƴ���
			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x - 30, point.y, paintText); // �����ı�
		}
		super.draw(canvas, mapView, shadow);
		// ����һ��drawable�߽磬ʹ�ã�0��0�������drawable�ײ����һ�����ĵ�һ������
		boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mGeoList.size();
	}

	// ��������¼�
	protected boolean onTap(int i) {
		setFocus(mGeoList.get(i));
		MapView.LayoutParams geoLP = (MapView.LayoutParams) mContext.popView
				.getLayoutParams();
		GeoPoint pt = mGeoList.get(i).getPoint();
		mContext.mMapView.updateViewLayout(mContext.popView,
				new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, pt,
						MapView.LayoutParams.BOTTOM_CENTER));
		mContext.popView.setVisibility(View.VISIBLE);
		/*textView1 = (TextView) mContext.findViewById(R.id.); 
		textView2 = (TextView) mContext.findViewById(R.id.map_bubbleText);
		textView1.setText("��ʾ��Ϣ");
		textView2.setText(mGeoList.get(i).getSnippet());
		ImageView imageView = (ImageView) mContext
				.findViewById(R.id.p);
		imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mContext.popView.setVisibility(View.GONE);
			}
		});*/
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		return super.onTap(arg0, arg1);
	}
}
