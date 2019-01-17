package com.jmaplus.pharmawine.utils;

public final class ApiRoutes {


    public static final String DevelopmentBaseRoute = "http://secure-gorge-78407.herokuapp.com/api/v1/";

    public static final String ProductionBaseRoute = "";

    public static final String baseRoute = DevelopmentBaseRoute;

    public static final String login = baseRoute + "/login";

    public static final String userDetails = baseRoute + "/user";

}
