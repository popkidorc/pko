package org.mybatis.extension.auto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mybatis.extension.auto.type.FieldType;

/**
 * 
 * Column annotation
 * 
 * @author popkidorc
 * @date 2015年3月30日
 * 
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Field {
	public abstract String fieldName() default "";

	public abstract ForeignKey[] fKey() default {};

	public abstract FieldType type() default FieldType.VARCHAR;

	public abstract int length() default 255;

	public abstract boolean nullable() default true;

	public abstract String fieldComments() default "";

}
