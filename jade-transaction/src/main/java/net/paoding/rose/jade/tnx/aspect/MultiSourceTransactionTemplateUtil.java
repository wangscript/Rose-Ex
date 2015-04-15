package net.paoding.rose.jade.tnx.aspect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import net.paoding.rose.jade.tnx.adapter.TnxDataAccessFactory;
import net.paoding.rose.jade.tnx.adapter.TnxDataSourceFactory;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.TransactionTemplate;

public class MultiSourceTransactionTemplateUtil {
	
	private static final Object updateTemplateLock = new Object();
	private static ConcurrentHashMap<CacheKey, TransactionTemplate> transactionTemplateMap = new ConcurrentHashMap<MultiSourceTransactionTemplateUtil.CacheKey, TransactionTemplate>();

	private static ConcurrentHashMap<DataSource, DataSourceTransactionManager> transactionManagerMap = new ConcurrentHashMap<DataSource, DataSourceTransactionManager>();
	private static final Object updateTransactionManagerLock = new Object();

	/**
	 * dongyu.cai
	 * 2014/12/22
	 * 根据多个dao返回多个数据源，
	 * 一样的数据源只会返回一个
	 */
	public static ArrayList<TransactionTemplate> getTransactionTemplates(Class<?>[] daoClassAry,Isolation isolation) {
		TnxDataSourceFactory dataSourceFactory = TnxDataAccessFactory.getDataSourceFactory();
		
		ArrayList<TransactionTemplate> list = new ArrayList<TransactionTemplate>();
		HashSet<String> buf = new HashSet<String>();
		for(Class<?> daoClass:daoClassAry){
			if(!buf.contains(daoClass.getName())){
				buf.add(daoClass.getName());
				DataSource dataSource = dataSourceFactory.getDataSourceHolder(daoClass).getDataSource();
				TransactionTemplate transcation = getTransactionTemplate(dataSource, isolation);
				list.add(transcation);
				System.out.println("获取数据源:"+daoClass.getName());
			}else{
				System.out.println("已存在数据源:"+daoClass.getSimpleName());
			}
		}
		return list;
	}
	

	private static TransactionTemplate getTransactionTemplate(
			DataSource dataSource, Isolation isolation) {
		CacheKey cacheKey = new CacheKey(dataSource, isolation);
		TransactionTemplate transactionTemplate = transactionTemplateMap
				.get(cacheKey);
		if (transactionTemplate == null) {
			synchronized (updateTemplateLock) {
				transactionTemplate = transactionTemplateMap.get(cacheKey);
				if (transactionTemplate == null) {
					DataSourceTransactionManager transactionManager = getDataSourceTransactionManager(dataSource);
					transactionTemplate = new TransactionTemplate(
							transactionManager);
					transactionTemplate.setIsolationLevel(isolation.value());
					transactionTemplateMap.put(cacheKey, transactionTemplate);
				}
			}
		}
		return transactionTemplate;

	}
	
	private static DataSourceTransactionManager getDataSourceTransactionManager(
			DataSource dataSource) {
		DataSourceTransactionManager transactionManager = transactionManagerMap
				.get(dataSource);
		if (transactionManager == null) {
			synchronized (updateTransactionManagerLock) {
				transactionManager = transactionManagerMap.get(dataSource);
				if (transactionManager == null) {
					transactionManager = new DataSourceTransactionManager(
							dataSource);
					transactionManagerMap.put(dataSource, transactionManager);
				}
			}
		}
		return transactionManager;
	}

	private static class CacheKey {
		@SuppressWarnings("unused")
		DataSource dataSource;
		@SuppressWarnings("unused")
		Isolation isolation;

		public CacheKey(DataSource dataSource, Isolation isolation) {
			this.dataSource = dataSource;
			this.isolation = isolation;
		}
	}
}
