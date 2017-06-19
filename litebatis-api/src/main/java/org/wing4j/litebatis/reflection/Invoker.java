package org.wing4j.litebatis.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * ִ����
 */
public interface Invoker {
    /**
     * ִ�а�װ�ķ���
     * @param target Ŀ�����
     * @param args ��������
     * @return ִ�н��
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

    /**
     * ������������
     * @return ����
     */
    Class<?> getType();
}
