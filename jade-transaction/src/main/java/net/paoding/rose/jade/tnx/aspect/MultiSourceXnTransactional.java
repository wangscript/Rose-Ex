package net.paoding.rose.jade.tnx.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Isolation;

/**
 * 标注了该注解的方法使用事务机制,只适用于事务中所有db操作都在同一个库的情况.事务中的读写操作都会在主库的写连接上执行,
 * 事务结束之后的读写操作分别在主库从库上执行.没有经过严格的测试,使用时打开log以便确认事务是否生效.具体实现详见
 * {@link XnTransactionalAspect#addTransaction(org.aspectj.lang.ProceedingJoinPoint, XnTransactional)}
 * 
 * @author xile.su@renren-inc.com
 * 
 *         2014年9月10日 下午1:55:37
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MultiSourceXnTransactional {

	/**
	 * 事务隔离级别
	 * 
	 * @return
	 */
	Isolation isolation() default Isolation.DEFAULT;

	/**
	 * 用来获取数据源,同一个事务中可以使用不同的的dao,只要他们的数据源一致就ok,在他们之中任选一个就行.
	 * 之所以不用String类型的bizName(即DAO标注的catalog)是因为测试时可以配置测试数据源,如果直接使用bizName
	 * 那么事务的连接是线上状态而业务中的dao接口使用测试库及时发送异常而回滚也不会有任何作用
	 * 
	 * @return
	 */
	Class<?>[] daoClass();

	/**
	 * 发生发生回滚时返回默认值,否则将会抛出异常.返回值类型是和它对应的返回值:int->0,boolean->false,对象->null
	 * 
	 * @return
	 */
	boolean returnDefaultValueWhileRollback() default false;

}