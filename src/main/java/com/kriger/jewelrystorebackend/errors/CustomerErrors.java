package com.kriger.jewelrystorebackend.errors;

public class CustomerErrors {
    public static final String EMPTY_EMAIL = "שדה האימייל לא יכול להיות ריק";
    public static final String INVALID_EMAIL_FORMAT = "פורמט אימייל לא תקין";
    public static final String EMAIL_ALREADY_IN_USE = "האימייל הזה כבר קיים במערכת";
    public static final String INVALID_FIRST_NAME = "שם פרטי לא תקין. השם חייב להכיל לפחות 2 אותיות וללא תווים מיוחדים";
    public static final String INVALID_LAST_NAME = "שם משפחה לא תקין. השם חייב להכיל לפחות 2 אותיות וללא תווים מיוחדים";
    public static final String EMPTY_PASSWORD = "חובה להזין סיסמה";
    public static final String WEAK_PASSWORD = "הסיסמה חלשה מדי, נדרשים לפחות 6 תווים, אות גדולה וספרה";
    public static final String INVALID_PHONE = "מספר הטלפון אינו תקין. יש להזין מספר נייד הכולל 10 ספרות ומתחיל בקידומת 05";
    public static final String INVALID_CREDENTIALS = "אימייל או סיסמה שגויים";
    public static final String CUSTOMER_NOT_FOUND = "הלקוח לא נמצא במערכת";
    public static final String CUSTOMER_BY_EMAIL_NOT_FOUND = "לא נמצא לקוח עם האימייל המבוקש";
    public static final String CUSTOMER_TO_DELETE_NOT_FOUND = "שגיאה: הלקוח שאתה מנסה למחוק לא קיים במערכת";
    public static final String INCORRECT_CURRENT_PASSWORD = "הסיסמה הנוכחית שגויה";
    public static final String DATABASE_SAVE_ERROR = "שגיאה פנימית: לא ניתן היה לשמור את הלקוח במערכת";
    public static final String DATABASE_FETCH_ERROR = "שגיאה בשליפת רשימת הלקוחות ממסד הנתונים";
}
