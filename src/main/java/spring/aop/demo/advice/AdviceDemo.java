package spring.aop.demo.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class AdviceDemo implements MethodBeforeAdvice, AfterReturningAdvice {

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("my before advice");
		// method.invoke(target, args); 如果再调用这句，则目标方法会执行多一次
	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("my after advice " + returnValue);
	}
}
