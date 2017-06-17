//package org.wing4j.litebatis.binding;
//
//import org.wing4j.litebatis.Configuration;
//import org.wing4j.litebatis.exception.BindingException;
//import org.wing4j.litebatis.session.SqlSession;
//
//import java.util.*;
//
///**
// * Created by wing4j on 2017/6/5.
// */
//public class DefaultMapperRegistry implements MapperRegistry{
//    Configuration config;
//    final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, MapperProxyFactory<?>>();
//
//    public DefaultMapperRegistry(Configuration config) {
//        this.config = config;
//    }
//    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
//        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
//        if (mapperProxyFactory == null)
//            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
//        try {
//            return mapperProxyFactory.newInstance(sqlSession);
//        } catch (Exception e) {
//            throw new BindingException("Error getting mapper instance. Cause: " + e, e);
//        }
//    }
//
//    public <T> boolean hasMapper(Class<T> type) {
//        return knownMappers.containsKey(type);
//    }
//
//    public <T> void addMapper(Class<T> type) {
//        if (type.isInterface()) {
//            if (hasMapper(type)) {
//                throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
//            }
//            boolean loadCompleted = false;
//            try {
////                knownMappers.put(type, new MapperProxyFactory<T>(type));
//                // It's important that the type is added before the parser is run
//                // otherwise the binding may automatically be attempted by the
//                // mapper parser. If the type is already known, it won't try.
////                MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);
////                parser.parse();
//                loadCompleted = true;
//            } finally {
//                if (!loadCompleted) {
//                    knownMappers.remove(type);
//                }
//            }
//        }
//    }
//
//    public Collection<Class<?>> getMappers() {
//        return Collections.unmodifiableCollection(knownMappers.keySet());
//    }
//
//    public void addMappers(String packageName, Class<?> superType) {
////        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
////        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
////        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
////        for (Class<?> mapperClass : mapperSet) {
////            addMapper(mapperClass);
////        }
//    }
//
//    public void addMappers(String packageName) {
//        addMappers(packageName, Object.class);
//    }
//}
