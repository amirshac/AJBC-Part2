package ajbc.learn.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import ajbc.learn.dao.DaoException;

@Aspect
@Component
public class MyAspect {

	
	//this is an advice method
	//syntax: (? optional)
	// "execution(modifier? return type?  method-pattern(arg-type, arg-type... or .. for nothing))"
	@Before("execution(* ajbc.learn.dao.ProductDao.count(..))")
	public void logBeforeCalling(JoinPoint joinPoint) {
		System.out.println("Aspect is writing to logger");
	}
	
	@Around("execution(* ajbc.lean.dao.ProductDao.get*(Double, Double))")
	public Object swapInputs(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Double min = (Double) args[0];
		Double max = (Double) args[1];
		if (max < min) {
			args = new Object[] {max, min};
		}
		return pjp.proceed(args);
	}
	
	@AfterThrowing(throwing = "ex", pointcut = "execution(* ajbc.lean.dao.ProductDao.*(..)")
	public void convertToDaoException(Throwable ex) throws DaoException {
		throw new DaoException(ex);
	}
}
