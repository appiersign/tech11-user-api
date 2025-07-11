package com.appiersign.util;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

@ApplicationScoped
public class NullAwareBeanUtil extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value != null) {
            super.copyProperty(dest, name, value);
        }
    }

}
