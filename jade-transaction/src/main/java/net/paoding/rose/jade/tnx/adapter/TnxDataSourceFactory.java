package net.paoding.rose.jade.tnx.adapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.dataaccess.DataSourceFactory;
import net.paoding.rose.jade.dataaccess.DataSourceHolder;
import net.paoding.rose.jade.statement.StatementMetaData;

import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TnxDataSourceFactory implements DataSourceFactory, ApplicationContextAware{

	private Log logger = LogFactory.getLog(getClass());
	
    private ListableBeanFactory applicationContext;
    
    private ConcurrentHashMap<Class<?>, DataSourceHolder> cachedDataSources = new ConcurrentHashMap<Class<?>, DataSourceHolder>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
	@Override
	public DataSourceHolder getHolder(StatementMetaData metaData,
			Map<String, Object> runtime) {
		Class<?> daoClass = metaData.getDAOMetaData().getDAOClass();
		DataSourceHolder holder = this.getDataSourceHolder(daoClass);
		return holder;
	}
	
	public DataSourceHolder getDataSourceHolder(Class<?> daoClass){
		DataSourceHolder holder = cachedDataSources.get(daoClass);
        if (holder != null) {
            return holder;
        }

        holder = getDataSourceByDirectory(daoClass, daoClass.getName());
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        String catalog = daoClass.getAnnotation(DAO.class).catalog();
        
        if (catalog.length() > 0) {
            holder = getDataSourceByDirectory(daoClass, catalog + "." + daoClass.getSimpleName());
        }
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        holder = getDataSourceByKey(daoClass, "jade.dataSource");
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        holder = getDataSourceByKey(daoClass, "dataSource");
        if (holder != null) {
            cachedDataSources.put(daoClass, holder);
            return holder;
        }
        return null;
	}
	
	public DataSourceHolder getDataSourceByDirectory(Class<?> daoClass, String catalog) {
        String tempCatalog = catalog;
        DataSourceHolder dataSource;
        while (tempCatalog != null && tempCatalog.length() > 0) {
            dataSource = getDataSourceByKey(daoClass, "jade.dataSource." + tempCatalog);
            if (dataSource != null) {
                return dataSource;
            }
            int index = tempCatalog.lastIndexOf('.');
            if (index == -1) {
                tempCatalog = null;
            } else {
                tempCatalog = tempCatalog.substring(0, index);
            }
        }
        return null;
    }

    private DataSourceHolder getDataSourceByKey(Class<?> daoClass, String key) {
        if (applicationContext.containsBean(key)) {
            Object dataSource = applicationContext.getBean(key);
            if (!(dataSource instanceof DataSource) && !(dataSource instanceof DataSourceFactory)) {
                throw new IllegalClassException("expects DataSource or DataSourceFactory, but a "
                        + dataSource.getClass().getName());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("found dataSource: " + key + " for DAO " + daoClass.getName());
            }
            return new DataSourceHolder(dataSource);
        }
        return null;
    }
}
