package cn.zzuisa.enums;

public enum VideoStatusEnum {
	SUCCESS(1), //发布成功
	FORBID(2),;//禁止播放

	public final int value;

	private VideoStatusEnum(int value) {
		this.value = value;
	};

	public int getValue() {
		return value;
	}

}
