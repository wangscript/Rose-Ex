package net.paoding.rose.jade.tnx.aspect;

import java.util.ArrayList;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

//import com.xiaonei.platform.jade.SQLThreadLocalUtil;
@Aspect
public class MultiSourceXnTransactionalAspect {

		@Around(value = "@annotation(xt)")
		public Object addTransaction(final ProceedingJoinPoint pjp,MultiSourceXnTransactional xt) throws Throwable {
			System.out.println("添加事务<tnx>: " + getTransactionMethod(pjp));
			ArrayList<TransactionTemplate> tnsList = MultiSourceTransactionTemplateUtil.getTransactionTemplates(xt.daoClass(), xt.isolation());
			Object result = excuteTnsList(tnsList, 0, pjp);
	
			if (result instanceof Throwable) {
				if (xt.returnDefaultValueWhileRollback()) { // 返回默认值,并打印异常
					((Throwable) result).printStackTrace();
					result = null;
				} else {
					throw (Throwable) result;
				}
			}
			System.out.println("退出事务<tnx>: " + getTransactionMethod(pjp));
			return result;
		}
		
		
		private Object excuteTnsList(final ArrayList<TransactionTemplate> tnsList,int level,final ProceedingJoinPoint pjp) throws Throwable{
			if(level < tnsList.size()){
				final int nextLevel = level+1;
				System.out.println("开启第"+nextLevel+"/"+tnsList.size()+"个事务");
				Object result = tnsList.get(level).execute(new TransactionCallback<Object>() {
					@Override
					public Object doInTransaction(TransactionStatus ts) {
						try {
							return excuteTnsList(tnsList,nextLevel,pjp);
						} catch (Throwable e) {
							ts.setRollbackOnly();
							System.out.println("回滚第"+nextLevel+"/"+tnsList.size()+"个事务 " );
							return e;
						}
					}
				});
				if(result instanceof Throwable){
					throw (Throwable) result;
				}else{
					return result;
				}
			}else{
				return pjp.proceed();
			}
		}
	
		/**
		 * 被添加事务的方法
		 * 
		 * @param pjp
		 * @return
		 */
		private String getTransactionMethod(ProceedingJoinPoint pjp) {
			return pjp.getTarget().getClass().getName() + "."
					+ pjp.getSignature().getName() + "()";
		}
}