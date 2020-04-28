package org.itachi.codestar.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.init.Jackson2ResourceReader;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by itachi on 2018/1/16.
 * User: itachi
 * Date: 2018/1/16
 * Time: 15:01
 *
 * @author itachi
 */
public abstract class RepositoryPopulator implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private final Jackson2ResourceReader resourceReader;
    private final Resource sourceData;
    private final Class<? extends CrudRepository> clazz;

    private ApplicationContext applicationContext;

    RepositoryPopulator(String sourceName, Class<? extends CrudRepository> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        resourceReader = new Jackson2ResourceReader(mapper);
        sourceData = new ClassPathResource(sourceName);
        this.clazz = clazz;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().equals(applicationContext)) {
            CrudRepository repository =
                    BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, clazz);

            if (repository != null && repository.count() == 0) {
                populate(repository);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public void populate(CrudRepository repository) {
        Object entity = getEntityFromResource(sourceData);

        if (entity instanceof Collection) {
            for (Serializable object : (Collection<Serializable>) entity) {
                if (object != null) {
                    repository.save(object);
                }
            }
        } else {
            repository.save(entity);
        }
    }

    private Object getEntityFromResource(Resource resource) {
        try {
            return resourceReader.readFrom(resource, this.getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
