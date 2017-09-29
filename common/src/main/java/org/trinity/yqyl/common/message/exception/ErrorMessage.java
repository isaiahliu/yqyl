package org.trinity.yqyl.common.message.exception;

import org.trinity.message.exception.IErrorMessage;

public enum ErrorMessage implements IErrorMessage {
    TOKEN_IS_EXPIRED,
    LOGGED_BY_OTHERS,
    USER_IS_DISABLED,
    PASSWORD_CHANGED,
    WRONG_PASSWORD,
    UNABLE_TO_ACCESS_USER,
    MULTI_ROOT_ACCESSRIGHT,
    TOKEN_IS_NOT_AUTHENTICATED,
    USERNAME_IS_REGISTERED,
    SUPPLIER_CLIENT_EXISTS,
    ONLY_PROPOSAL_CLIENT_CAN_BE_CANCELLED,
    SERVICE_SUPPLIER_CLIENT_MUST_BE_PROPOSAL,
    SERVICE_RECEIVER_CLIENT_MUST_BE_PROPOSAL,
    NO_SUB_ORDERS,
    INVALID_ORDER_ID,
    INCORRECT_SERVICE_ORDER_STATUS,
    INSUFFICIENT_ACCESSRIGHT,
    UNABLE_TO_FIND_USER,
    NO_USER_BINDING_TO_YIQUAN_CODE,
    INSUFFICIENT_BALANCE,
    BENEFICIARY_SUPPLIER_MISMATCH,
    NO_YIQUAN_BINDED,
    TOPUP_AMOUNT_MUST_BE_POSITIVE,
    CELLPHONE_IS_REGISTERED,
    VERIFY_CODE_IS_EXPIRED,
    INCORRECT_VERIFY_CODE,
    YIQUAN_IS_ALREADY_BINDED,
    CLIENT_SHOULD_BE_REALNAME,
    INVALID_YIQUAN_CODE,
    EXCEED_ALLOWED_SIZE,
    INCORRECT_SERVICE_RECEIVER_CLIENT_STATUS,
    NEW_PASSWORD_SHOULD_BE_DIFFERENT,
    INCORRECT_STATUS,
    UNABLE_TO_CONNECT_YKT,
    ACCOUNT_LOCKED,
    PAYMENT_EXCEEDS_ALLOWED_ALLOWANCE,
    CANNOT_FIND_TRANSACTION;

    @Override
    public String getMessageCode() {
        return name();
    }
}
