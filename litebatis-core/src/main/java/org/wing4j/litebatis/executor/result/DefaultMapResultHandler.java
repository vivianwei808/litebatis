package org.wing4j.litebatis.executor.result;

import org.wing4j.litebatis.reflection.ReflectorFactory;
import org.wing4j.litebatis.reflection.factory.ObjectFactory;
import org.wing4j.litebatis.reflection.wrapper.ObjectWrapperFactory;
import org.wing4j.litebatis.session.ResultContext;
import org.wing4j.litebatis.session.ResultHandler;

import java.util.Map;

public class DefaultMapResultHandler<K, V> implements ResultHandler<V> {

  private final Map<K, V> mappedResults;
  private final String mapKey;
  private final ObjectFactory objectFactory;
  private final ObjectWrapperFactory objectWrapperFactory;
  private final ReflectorFactory reflectorFactory;

  @SuppressWarnings("unchecked")
  public DefaultMapResultHandler(String mapKey, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
    this.objectFactory = objectFactory;
    this.objectWrapperFactory = objectWrapperFactory;
    this.reflectorFactory = reflectorFactory;
    this.mappedResults = objectFactory.create(Map.class);
    this.mapKey = mapKey;
  }

  @Override
  public void handleResult(ResultContext<? extends V> context) {
    final V value = context.getResultObject();
//    final MetaObject mo = MetaObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
//    // TODO is that assignment always true?
//    final K key = (K) mo.getValue(mapKey);
//    mappedResults.put(key, value);
  }

  public Map<K, V> getMappedResults() {
    return mappedResults;
  }
}
