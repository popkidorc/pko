package org.mybatis.extension.auto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mybatis.extension.auto.type.IdType;

/**
 * 
 * Id column annotation
 * 
 * @author popkidorc
 * @date 2015年3月30日
 * 
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Id {

	public abstract IdType idType() default IdType.SIMPLE;

}
