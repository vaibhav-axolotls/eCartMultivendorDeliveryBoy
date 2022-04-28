package com.axolotls.prachetadboy.helper;

import java.text.DecimalFormat;

public class Constant {
    //MODIFICATION PART

    public static final String BASE_URL = "https://pracheta.co/admin/"; //Admin panel url with it whould be nessesary to put "/"(slash) at end of the url (https://admin.panel.url/)

    //set your jwt secret key here...key must same in PHP and Android
    public static final String JWT_KEY = "pra123@_12@_!*_app_34@04_re_rcheta123";

    //MODIFICATION PART END

    public static final String SUB_URL = "delivery-boy/";
    public static final String MAIN_URL = BASE_URL + SUB_URL + "api/api-v1.php";
    public static final String DELIVERY_BOY_POLICY = BASE_URL + "delivery-boy-play-store-privacy-policy.php";
    public static final String DELIVERY_BOY_TERMS = BASE_URL + "delivery-boy-play-store-terms-conditions.php";
    public static final String AccessKey = "accesskey";
    public static final String AccessKeyVal = "90336";
    public static final String GetVal = "1";
    public static final String LOGIN = "login";
    public static final String GET_DELIVERY_BOY_BY_ID = "get_delivery_boy_by_id";
    public static final String UPDATE_DELIVERY_BOY_PROFILE = "update_delivery_boy_profile";
    public static final String BULK_STATUS_UPDATE = "bulk_status_update";
    public static final String GET_WITHDRAWAL_REQUEST = "get_withdrawal_requests";
    public static final String SEND_WITHDRAWAL_REQUEST = "send_withdrawal_request";
    public static final String TYPE = "type";
    public static final String DELIVERY_BOY = "delivery_boy";
    public static final String DELIVERY_BOY_FORGOT_PASSWORD = "delivery_boy_forgot_password";
    public static final String GET_NOTIFICATION = "get_notifications";
    public static final String CHECK_DELIVERY_BOY_BY_MOBILE = "check_delivery_boy_by_mobile";
    public static final String ID = "id";
    public static CharSequence[] filtervalues = {"Show Wallet Transactions", "Show Wallet Requests"};
    public static final String WITHDRAWAL_REQUEST = "withdrawal_requests";
    public static final String FUND_TRANSFERS = "fund_transfers";
    public static final String TYPE_ID = "type_id";
    public static final String DELIVERY_BOY_ID = "delivery_boy_id";
    public static final String GET_ORDERS = "get_orders";
    public static final String CHANGE_AVAILABILITY = "change_availability";
    public static final String IS_AVAILABLE = "is_available";
    public static final String UPDATE_DELIVERY_BOY_FCM_ID = "update_delivery_boy_fcm_id";
    public static final String ITEM_IDS = "item_ids";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String ADDRESS = "address";
    public static final String BONUS = "bonus";
    public static final String BALANCE = "balance";
    public static final String CURRENCY = "currency";
    public static final String UPDATED_BALANCE = "updated_balance";
    public static final String STATUS = "status";
    public static final String CREATED_AT = "date_created";
    public static final String DATA = "data";
    public static final String DATA_TYPE = "data_type";
    public static final String TOTAL = "total";
    public static final String OLD_PASSWORD = "old_password";
    public static final String UPDATE_PASSWORD = "update_password";
    public static final String CONFIRM_PASSWORD = "confirm_password";
    public static final String MESSAGE = "message";
    public static final String AMOUNT = "amount";
    public static final String FROM = "from";
    public static final String ORDER_ITEM_ID = "order_item_id";
    public static final String POSITION = "position";
    public static final String ITEM = "item";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String FCM_ID = "fcm_id";
    public static final String AWAITING_PAYMENT = "awaiting_payment";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String RECEIVED = "Received";
    public static final String PROCESSED = "Processed";
    public static final String SHIPPED = "Shipped";
    public static final String DELIVERED = "Delivered";
    public static final String CANCELLED = "Cancelled";
    public static final String RETURNED = "Returned";
    public static final String SHOW = "show";
    public static final String HIDE = "hide";
    public static final String ERROR = "error";
    public static final int LOAD_ITEM_LIMIT = 10;
    public static DecimalFormat formatter = new DecimalFormat("0.00");

    public static String country_code = "";
    public static String verificationCode;
    public static String PRODUCT_LOAD_LIMIT = "10";
    public static boolean CLICK = false;

}
