package org.mybatis.extension.auto.type;

/**
 * Automatically create table type constant : 'CREATE'. Drop tables and data ,
 * and create tables.
 * 
 * Automatically create table type constant : 'UPDATE'. Don't drop tables
 * (including columns) and data , and alter new columns.
 * 
 * Automatically create table type constant : 'NONE'. Don't do any operation.
 * 
 * @author popkidorc
 * @date 2015年4月1日
 * 
 */
public enum AutoType {
	CREATE, UPDATE, NONE;
}
