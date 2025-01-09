package org.example.paymentservice.util;

public final class ExceptionConstants {

    public static final String NOT_ENOUGH_MONEY = "Not enough money on balance";
    public static final String CUSTOMER_NOT_FOUND = "Customer with id = %s not found";

    public static final String NAME_NOT_BLANK = "Name cannot be blank";
    public static final String NAME_SIZE = "Name cant be greater than 255 symbols";
    public static final String BALANCE_POSITIVE = "Balance must be positive or zero";

    public static final String CARD_NUMBER_NOT_BLANK = "Card number cannot be blank";
    public static final String CARD_NUMBER_REGEX = "\\d{16}";
    public static final String CARD_NUMBER_REGEX_MESSAGE = "Card number must consist of exactly 16 digits";

    public static final String CARD_EXPIRATION_MONTH = "Expiration month must not be null";
    public static final String CARD_EXPIRATION_MONTH_SIZE_MIN = "Expiration month cant be less than 1";
    public static final String CARD_EXPIRATION_MONTH_SIZE_MAX = "Expiration month cant be greater than 12";
    public static final String CARD_EXPIRATION_YEAR = "Expiration year must not be null";

    public static final String CARD_CVC_NOT_BLANK = "Card CVC cannot be blank";
    public static final String CARD_CVC_REGEX = "\\d{3}";
    public static final String CARD_CVC_REGEX_MESSAGE = "CVC must be 3 digits";

    public static final String CUSTOMER_ID_NOT_BLANK = "Customer ID must not be blank";
    public static final String CURRENCY_NOT_BLANK = "Currency must not be blank";
    public static final String CURRENCY_REGEX = "[A-Z]{3}";
    public static final String CURRENCY_REGEX_MESSAGE = "Currency must be a valid ISO 4217 code (3 uppercase letters)";
    public static final String AMOUNT_NOT_NULL = "Amount must not be null";
    public static final String AMOUNT_MIN = "Amount must be greater than 0";
    public static final String TOKEN_NOT_BLANK = "Token must not be blank";

    private ExceptionConstants() {}
}