package com.msk3cloud.core.condition;

/**
 * @author Rao
 * @Date 2022/01/13
 **/
@Deprecated
public interface Condition  {
//
//    /**
//     * 等于
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition eq(String key, Object val){
//        this.eq( true,key,val);
//        return this;
//    }
//
//    /**
//     * 等于
//     * @param key
//     * @param val
//     * @return
//     */
//    Condition eq(boolean condition, String key, Object val);
//
//    /**
//     * 大于
//     * @return
//     */
//    Condition gt(boolean condition, String key, Object val);
//
//    /**
//     * 大于
//     * @return
//     */
//    default Condition gt(String key, Object val){
//        this.gt( true,key,val );
//        return this;
//    }
//
//    /**
//     * 不等于
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition ne(String key, Object val){
//        this.ne(true,key,val);
//        return this;
//    }
//
//    /**
//     * 不等于
//     * @param key
//     * @param val
//     * @return
//     */
//    Condition ne(boolean condition, String key, Object val);
//
//    /**
//     * 包含
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition in(String key, Object... val){
//        this.in(true,key,val);
//        return this;
//    }
//
//    /**
//     * 包含
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition in(boolean condition, String key, Object... val){
//        if(condition){
//            List<Object> paramsList = Arrays.asList(val);
//            this.in( key,paramsList);
//        }
//        return this;
//    }
//
//
//    /**
//     * 包含
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition in(String key, List<Object> val){
//        this.in(true,key,val);
//        return this;
//    }
//
//    /**
//     * 包含
//     * @param key
//     * @param val
//     * @return
//     */
//    Condition in(boolean condition, String key, List<Object> val);
//
//    /**
//     * like
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition like(String key, String val){
//        this.like(true,key,"%"+val+"%");
//        return this;
//    }
//
//    /**
//     * like
//     * @param key
//     * @param val
//     */
//    Condition like(boolean condition,String key,String val);
//
//    /**
//     * like
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition likeLeft(boolean condition, String key, String val){
//        this.like(condition,key,val+"%");
//        return this;
//    }
//
//
//    /**
//     * like
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition likeLeft(String key, String val){
//        this.like(true,key,val+"%");
//        return this;
//    }
//
//    /**
//     * like
//     * @param key
//     * @param val
//     * @return
//     */
//    default QueryCondition likeRight(boolean condition, String key, String val){
//        this.like(condition,key,"%" + val);
//        return this;
//    }
//
//    /**
//     * like
//     * @param key
//     * @param val
//     * @return
//     */
//    default Condition likeRight(String key, String val){
//        this.like(true,key,"%" + val);
//        return this;
//    }
//
//    /**
//     * 升序
//     * @param key
//     * @return
//     */
//    default Condition orderByAsc(String key){
//        this.orderBy(true,key, SqlKeyword.ASC.getSqlSegment());
//        return this;
//    }
//
//    /**
//     * 升序
//     * @param key
//     * @return
//     */
//    default Condition orderByAsc(boolean condition, String key){
//        this.orderBy(condition,key, SqlKeyword.ASC.getSqlSegment());
//        return this;
//    }
//
//    /**
//     * 升序
//     * @param key
//     * @return
//     */
//    default Condition orderByAsc(String... key){
//        for (String field : key) {
//            this.orderByAsc( field);
//        }
//        return this;
//    }
//
//    /**
//     * 降序
//     * @param key
//     * @return
//     */
//    default Condition orderByDesc(String... key){
//        for (String field : key) {
//            this.orderByDesc( field);
//        }
//        return this;
//    }
//
//    Condition orderBy(boolean condition, String key, String orderBy);
//
//    /**
//     * 降序
//     * @param key
//     * @return
//     */
//    default Condition orderByDesc(String key){
//        return this.orderBy(true,key, SqlKeyword.DESC.getSqlSegment());
//    }
//    /**
//     * 降序
//     * @param key
//     * @return
//     */
//    Condition orderByDesc(boolean condition, String key);
//
//    /**
//     * limit
//     * @param limit
//     */
//    default Condition limit(int limit){
//        return this.limit(true, limit);
//    }
//
//    /**
//     * limit
//     * @param limit
//     */
//    Condition limit(boolean condition,int limit);
//
//    /**
//     * page
//     * @param page
//     * @param limit
//     */
//    default Condition page(int page,int limit){
//        return this.page(true, page, limit);
//    }
//
//    /**
//     * page
//     * @param page
//     * @param limit
//     */
//    Condition page(boolean condition,int page,int limit);
//

}
