/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 * <p>
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * @author tpoola on 4/20/2017
 */
package com.pej.utils;

import java.util.Collection;
import java.util.List;

public class AlCollectionUtils {
    public static boolean isEmpty(Collection entity) {
        return (null==entity || entity.isEmpty());
    }

    public static boolean isEmpty(Object [] objects) {
        return (null==objects || objects.length == 0);
    }

    public static boolean isNotEmpty(List entityList) {
        return (null != entityList && (!entityList.isEmpty()));
    }

    public static boolean isNotEmpty(Collection collection){
            return (null != collection && (!collection.isEmpty()));
    }

    public static boolean isNull(Collection<? extends Object> entity) {return entity == null; }

    public static boolean isNotNull(Collection<? extends Object> entity) {return entity != null; }

    public static boolean isNotEmpty(Object [] objects) {
        return (null!=objects && objects.length != 0);
    }
}
