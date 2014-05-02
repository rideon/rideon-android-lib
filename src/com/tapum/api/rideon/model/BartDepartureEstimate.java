package com.tapum.api.rideon.model;

/**
 * 
 * @author Neeraj Jain
 *
 */
public class BartDepartureEstimate {

	private String minutes;
	private int platform;
	private String direction;
	private String length;
	private String color;
	private String hexColor;
	private int bikeFlag;

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public int getBikeFlag() {
		return bikeFlag;
	}

	public void setBikeFlag(int bikeFlag) {
		this.bikeFlag = bikeFlag;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BartDepartureEstimate [minutes=");
		builder.append(minutes);
		builder.append(", platform=");
		builder.append(platform);
		builder.append(", direction=");
		builder.append(direction);
		builder.append(", length=");
		builder.append(length);
		builder.append(", color=");
		builder.append(color);
		builder.append(", hexColor=");
		builder.append(hexColor);
		builder.append(", bikeFlag=");
		builder.append(bikeFlag);
		builder.append("]");
		return builder.toString();
	}
}
