package enums;

/**
 *
 * @author Như Tâm
 */
public enum PromotionType {
	PRODUCT(0), ORDER(1);

	private final int value;

	private PromotionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean compare(int value) {
		return this.value == value;
	}

	public static PromotionType fromInt(int value) {
		for (PromotionType type : values()) {
			if (type.compare(value)) {
				return type;
			}
		}
		return null;
	}
}
