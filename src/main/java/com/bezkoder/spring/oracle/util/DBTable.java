package com.bezkoder.spring.oracle.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
	public @interface DBTable {
	public String columnName();
}