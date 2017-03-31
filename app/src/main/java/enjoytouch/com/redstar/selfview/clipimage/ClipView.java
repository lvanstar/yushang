package enjoytouch.com.redstar.selfview.clipimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ClipView extends View {
	private Paint paint = new Paint();
	private Paint borderPaint = new Paint();

	/** �Զ��嶥�����߶ȣ��粻���Զ��壬��Ĭ��Ϊ0���� */
	private int customTopBarHeight = 0;
	/** �ü��򳤿�ȣ�Ĭ��4��3 */
	private double clipRatio = 0.75;
	/** �ü����� */
	private int clipWidth = -1;
	/** �ü���߶� */
	private int clipHeight = -1;
	/** �ü�����߿������ */
	private int clipLeftMargin = 0;
	/** �ü����ϱ߿������ */
	private int clipTopMargin = 0;
	/** �ü���߿��� */
	private int clipBorderWidth = 1;
	private boolean isSetMargin = false;
	private OnDrawListenerComplete listenerComplete;

	public ClipView(Context context) {
		super(context);
	}

	public ClipView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ClipView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = this.getWidth();
		int height = this.getHeight();
		// ��û����ʾ���òü���߶ȺͿ�ȣ�ȡĬ��ֵ
		if (clipWidth == -1 || clipHeight == -1) {
			clipWidth = width - 50;
			clipHeight = (int) (clipWidth * clipRatio);
			// ����
			if (width > height) {
				clipHeight = height - 50;
				clipWidth = (int) (clipHeight / clipRatio);
			}
		}
		// ��û����ʾ���òü��������Ԥ����ȣ�ȡĬ��ֵ
		if (!isSetMargin) {
			clipLeftMargin = (width - clipWidth) / 2;
			clipTopMargin = (height - clipHeight) / 2;
		}
		// ����Ӱ
		paint.setAlpha(200);
		// top
		canvas.drawRect(0, customTopBarHeight, width, clipTopMargin, paint);
		// left
		canvas.drawRect(0, clipTopMargin, clipLeftMargin, clipTopMargin
				+ clipHeight, paint);
		// right
		canvas.drawRect(clipLeftMargin + clipWidth, clipTopMargin, width,
				clipTopMargin + clipHeight, paint);
		// bottom
		canvas.drawRect(0, clipTopMargin + clipHeight, width, height, paint);

		// ���߿�
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(Color.WHITE);
		borderPaint.setStrokeWidth(clipBorderWidth);
		canvas.drawRect(clipLeftMargin, clipTopMargin, clipLeftMargin
				+ clipWidth, clipTopMargin + clipHeight, borderPaint);

		if (listenerComplete != null) {
			listenerComplete.onDrawCompelete();
		}
	}

	public int getCustomTopBarHeight() {
		return customTopBarHeight;
	}

	public void setCustomTopBarHeight(int customTopBarHeight) {
		this.customTopBarHeight = customTopBarHeight;
	}

	public double getClipRatio() {
		return clipRatio;
	}

	public void setClipRatio(double clipRatio) {
		this.clipRatio = clipRatio;
	}

	public int getClipWidth() {
		// ��clipBorderWidthԭ�򣺽�ͼʱȥ���߿����
		return clipWidth - clipBorderWidth;
	}

	public void setClipWidth(int clipWidth) {
		this.clipWidth = clipWidth;
	}

	public int getClipHeight() {
		return clipHeight - clipBorderWidth;
	}

	public void setClipHeight(int clipHeight) {
		this.clipHeight = clipHeight;
	}

	public int getClipLeftMargin() {
		return clipLeftMargin + clipBorderWidth;
	}

	public void setClipLeftMargin(int clipLeftMargin) {
		this.clipLeftMargin = clipLeftMargin;
		isSetMargin = true;
	}

	public int getClipTopMargin() {
		return clipTopMargin + clipBorderWidth;
	}

	public void setClipTopMargin(int clipTopMargin) {
		this.clipTopMargin = clipTopMargin;
		isSetMargin = true;
	}

	public void addOnDrawCompleteListener(OnDrawListenerComplete listener) {
		this.listenerComplete = listener;
	}

	public void removeOnDrawCompleteListener() {
		this.listenerComplete = null;
	}

	/**
	 * �ü�������ʱ���ýӿ�
	 * 
	 * @author Cow
	 * 
	 */
	public interface OnDrawListenerComplete {
		public void onDrawCompelete();
	}

}
