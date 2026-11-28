package com.kriger.jewelrystorebackend.errors;

public class ProductErrors {
    public static final String INVALID_PRODUCT = "פרטי המוצר אינם תקינים.";
    public static final String INVALID_PRODUCT_ID = "מזהה המוצר אינו תקין.";
    public static final String PRODUCT_NOT_FOUND = "המוצר לא נמצא.";
    public static final String INVALID_CATEGORY_ID = "מזהה הקטגוריה אינו תקין.";
    public static final String CATEGORY_NOT_FOUND = "הקטגוריה לא נמצאה.";
    public static final String INVALID_PRODUCT_NAME = "שם המוצר אינו תקין.";
    public static final String INVALID_BASE_SKU = "מק\"ט הבסיס של המוצר אינו תקין.";
    public static final String INVALID_PRODUCT_SLUG = "כתובת המוצר אינה תקינה.";
    public static final String PRODUCT_SKU_ALREADY_EXISTS = "כבר קיים מוצר עם מק\"ט זה.";
    public static final String PRODUCT_SLUG_ALREADY_EXISTS = "כבר קיים מוצר עם כתובת זו.";
    public static final String INVALID_DIAMONDS_COUNT = "כמות היהלומים אינה תקינה.";
    public static final String INVALID_DIAMONDS_WEIGHT = "משקל היהלומים אינו תקין.";
    public static final String EMPTY_PRODUCT_LIST = "אין מוצרים בחנות.";
    public static final String PRODUCT_ADD_FAILED = "שגיאה בשמירת המוצר במסד הנתונים.";
    public static final String PRODUCT_DELETE_FAILED = "שגיאה במחיקת המוצר ממסד הנתונים.";
    public static final String DATABASE_ERROR = "שגיאה במסד הנתונים בעת ביצוע הפעולה.";
    public static final String PRODUCT_UPDATE_FAILED = "לא ניתן לעדכן את המוצר.";
}