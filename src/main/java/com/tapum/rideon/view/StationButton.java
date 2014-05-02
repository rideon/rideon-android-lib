package com.tapum.rideon.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;

import com.tapum.rideon.lib.R;

public class StationButton extends Button {

	public StationButton(Context context) {
		super(context);
		setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);
		setPadding(0, 0, 0, 0);
		setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		setBackground(context.getResources()
				.getDrawable(R.drawable.roundbutton));
		setTextColor(Color.rgb(255, 255, 255));

	}

}
