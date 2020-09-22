package com.wisrc.sys.webapp.utils;

import java.util.UUID;

public class Toolbox {

    public static final String UUIDrandom() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
