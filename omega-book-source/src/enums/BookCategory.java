/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author thanhcanhit
 */
public enum BookCategory {
    LITERATURE(1),
    ECONOMICS(2),
    PSYCHOLOGY(3),
    CHILDRENS_BOOK(4),
    PARENTING(5),
    BIOGRAPHY(6),
    TEXTBOOK_REFERENCE(7),
    LANGUAGE_LEARNING(8);

    private final int value;

    private BookCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean compare(int value) {
        return this.value == value;
    }

    public static BookCategory fromInt(int value) {
        for (BookCategory type : values()) {
            if (type.compare(value)) {
                return type;
            }
        }
        return null;
    }
}
