package com.tapum.rideon.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;

import com.tapum.rideon.lib.R;

public class RouteButton extends Button {
	public RouteButton(Context context) {
		super(context);
		setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);

		setPadding(0, 0, 0, 0);
		setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		setTextColor(Color.rgb(255, 255, 255));

	}

	public void setBackgroundForRow(boolean even) {
		if (even) {
			setBackground(getContext().getResources().getDrawable(
					R.drawable.roundbutton));
		}// This works
			// with API
			// level 15
		else {
			setBackground(getContext().getResources().getDrawable(
					R.drawable.roundlightbutton));
		}
	}

}
