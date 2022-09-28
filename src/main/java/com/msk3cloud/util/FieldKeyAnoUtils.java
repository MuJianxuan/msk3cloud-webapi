package com.msk3cloud.util;

import com.msk3cloud.core.fieldparse.FieldKey;
import org.springframework.util.CollectionUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * {@link com.msk3cloud.core.fieldparse.FieldKey 注解的工具}
 * @author Rao
 * @Date 2022/01/12
 **/
public class FieldKeyAnoUtils {

    /**
     * 类字段与注解name map 的弱引用缓存
     */
    private static final Map<Class<?>, WeakReference<Map<String, String>>> CLASS_FIELD_MAP = new ConcurrentHashMap<>();

    /**
     * 获取字段类型集合
     * @return
     */
    public static List<String> getClassFieldList(Class<?> cls, List<String> fieldKeyList){

        if(CollectionUtils.isEmpty( fieldKeyList) ){
            return new ArrayList<>();
        }
        Map<String, String> fieldKeyFieldNameMap = getClassFieldMap(cls);
        return fieldKeyList.stream().map( fieldKeyFieldNameMap::get).filter(Objects::nonNull).collect(Collectors.toList());

    }

    public static Map<String, String> getClassFieldMap(Class<?> cls){
        WeakReference<Map<String, String>> mapWeakReference = CLASS_FIELD_MAP.get(cls);
        Map<String, String> fieldKeyFieldNameMap = null;
        if( mapWeakReference != null){
            fieldKeyFieldNameMap = mapWeakReference.get();
        }
        if(CollectionUtils.isEmpty( fieldKeyFieldNameMap )){
            fieldKeyFieldNameMap = parseClassField( cls);
        }
        return fieldKeyFieldNameMap;
    }
    /**
     * 解析类字段
     * @param cls
     * @return
     */
    private static Map<String, String> parseClassField(Class<?> cls){
        FieldKey clsFieldKey = cls.getAnnotation( FieldKey.class );
        String clsFieldHead = null;
        if( clsFieldKey != null){
            clsFieldHead = clsFieldKey.value();
        }
        Field[] fields = cls.getDeclaredFields();
        Map<String, String> fieldKeyFieldNameMap = new HashMap<>();
        // 将字段的类型进行替换成实际对象的字段名称
        for (Field field : fields) {
            FieldKey fieldKey = field.getAnnotation(FieldKey.class);
            if( fieldKey != null){
                String name = field.getName();
                String fieldKeyName = fieldKey.value();
                if( clsFieldHead != null){
                    fieldKeyName = clsFieldHead +"." + fieldKeyName;
                }
                fieldKeyFieldNameMap.put( fieldKeyName,name );
            }
        }
        CLASS_FIELD_MAP.put( cls, new WeakReference<>( fieldKeyFieldNameMap ) );
        return fieldKeyFieldNameMap;
    }

    /**
     * 获取字段Key集合
     * @param cls
     * @return
     */
    public static List<String> getFieldKeyList(Class<?> cls){
        Map<String, String> fieldKeyFieldNameMap = getClassFieldMap(cls);

        List<String> fieldKeyList = new ArrayList<>();
        Set<Map.Entry<String, String>> entrySet = fieldKeyFieldNameMap.entrySet();
        for (Map.Entry<String, String> fieldKeyFieldNameEntry : entrySet) {
            fieldKeyList.add( fieldKeyFieldNameEntry.getKey() );
        }
        return fieldKeyList;
    }

}
