package com.tapum.rideon.view;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public class DisplayOptions {

	private boolean displayLength;
	private boolean displayBikeInfo;
	private boolean displayColor;

	public boolean isDisplayLength() {
		return displayLength;
	}

	public void setDisplayLength(boolean displayLength) {
		this.displayLength = displayLength;
	}

	public boolean isDisplayBikeInfo() {
		return displayBikeInfo;
	}

	public void setDisplayBikeInfo(boolean displayBikeInfo) {
		this.displayBikeInfo = displayBikeInfo;
	}

	public boolean isDisplayColor() {
		return displayColor;
	}

	public void setDisplayColor(boolean displayColor) {
		this.displayColor = displayColor;
	}
}
