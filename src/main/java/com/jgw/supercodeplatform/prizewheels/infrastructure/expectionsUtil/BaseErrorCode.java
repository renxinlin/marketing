package com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil;

public interface BaseErrorCode {
    int HTTP_BAD_SERVER = 500;

    int getHttpCode();

    String getErrorMessage();

    String getInternalErrorCode();
}
