package com.tapum.rideon.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * This layout will be used to display the station buttons.
 * 
 * @author Neeraj Jain
 * 
 */
public class StationButtonRelativeLayout extends RelativeLayout.LayoutParams {

	private int LEFT_MARGIN = 4;
	private int TOP_MARGIN = 1;
	private int RIGHT_MARGIN = 0;
	private int BOTTOM_MARGIN = 0;

	public StationButtonRelativeLayout(Context context) {
		super(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		setMargins(LEFT_MARGIN, TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN);
		width = ViewGroup.LayoutParams.MATCH_PARENT;
		height = (int) (context.getResources().getDisplayMetrics().density * 40);// 80
	}
}
