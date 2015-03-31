package org.mybatis.extension.auto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Table annotation
 * 
 * @author popkidorc
 * @date 2015年3月30日
 * 
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Entity {

	public abstract String tableName() default "";

	public abstract String tableComments() default "";
}
