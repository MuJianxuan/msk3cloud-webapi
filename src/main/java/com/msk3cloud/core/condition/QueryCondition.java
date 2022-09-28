package com.msk3cloud.core.condition;

import com.alibaba.fastjson.JSON;
import com.kingdee.bos.webapi.sdk.QueryParam;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * sql 条件类
 *   够用就行 不够再加
 * @author Rao
 * @Date 2022/01/12
 **/
public class QueryCondition {

    /**
     * 最大分页 限制  金蝶限制每一页最大只能查 2000  起始是从 0
     */
    public final static Integer MAX_PAGE_LIMIT = 1999;

    /**
     * 表单ID
     */
    private final String formId;

    /**
     * 限制(页数)  最大2000
     */
    private Integer limit;

    /**
     * 页 page
     */
    private Integer page;

    /**
     * 字段key
     */
    private final Set<String> selectFieldList = new HashSet<>();

    /**
     * 条件查询
     */
    private final List<String> whereList = new ArrayList<>();

    /**
     * order by
     */
    private final List<String> orderByList = new ArrayList<>();

    /**
     * orSql
     */
    private String orSql = "";

    private QueryCondition(String formId){
        this.formId = formId;
    }

    public static QueryCondition build(String formId){
        return new QueryCondition( formId);
    }

    /**
     * 添加查询字段
     * @param sqlField
     * @return
     */
    public QueryCondition select(String sqlField){
        selectFieldList.add( sqlField);
        return this;
    }

    /**
     * 添加多个查询字段
     * @param sqlFields
     * @return
     */
    public QueryCondition select(String... sqlFields){
        selectFieldList.addAll( Arrays.asList( sqlFields));
        return this;
    }

    /**
     * 添加多个查询字段
     * @return
     */
    public QueryCondition select(List<String> fieldKeyList){
        if (! CollectionUtils.isEmpty(fieldKeyList)) {
            selectFieldList.addAll( fieldKeyList );
        }

        return this;
    }

    /**
     * 等于
     * @param key
     * @param val
     * @return
     */
    public QueryCondition eq(String key, Object val){
        this.eq( true,key,val);
        return this;
    }

    /**
     * 等于
     * @param key
     * @param val
     * @return
     */
    public QueryCondition eq(boolean condition, String key, Object val){
        if( condition){
            whereList.add( key + SqlKeyword.EQ.getSqlSegment() + this.parseVal( val) );
        }
        return this;
    }

    /**
     * 不为空
     * @param key
     * @return
     */
    public QueryCondition isNotNull(boolean condition, String key){
        if( condition){
            whereList.add( key + SqlKeyword.IS_NOT_NULL.getSqlSegment() );
        }
        return this;
    }


    /**
     * 不为空
     * @param key
     * @return
     */
    public QueryCondition isNotNull(String key){
        this.isNotNull( true,key);
        return this;
    }

    /**
     * 大于
     * @return
     */
    public QueryCondition gt(boolean condition, String key, Object val){
        if( condition ){
            whereList.add( key + SqlKeyword.GT.getSqlSegment() + this.parseVal( val)   );
        }
        return this;
    }

    /**
     * 大于
     * @return
     */
    public QueryCondition gt(String key, Object val){
        this.gt( true,key,val );
        return this;
    }

    /**
     * 大于等于
     * @return
     */
    public QueryCondition ge(boolean condition, String key, Object val){
        if( condition ){
            whereList.add( key + SqlKeyword.GE.getSqlSegment() + this.parseVal( val)   );
        }
        return this;
    }

    /**
     * 大于等于
     * @return
     */
    public QueryCondition ge(String key, Object val){
        this.gt( true,key,val );
        return this;
    }

    /**
     * 小于
     * @return
     */
    public QueryCondition lt(String key,Object val) {
        this.lt(true,key,val);
        return this;
    }

    /**
     * 小于
     * @param condition
     * @param key
     * @param val
     * @return
     */
    public QueryCondition lt(boolean condition, String key, Object val) {
        if(condition){
            whereList.add( key + SqlKeyword.LT.getSqlSegment() + this.parseVal(val) );
        }
        return this;
    }

    /**
     * 小于等于
     * @return
     */
    public QueryCondition le(String key,Object val) {
        this.lt(true,key,val);
        return this;
    }

    /**
     * 小于等于
     * @param condition
     * @param key
     * @param val
     * @return
     */
    public QueryCondition le(boolean condition, String key, Object val) {
        if(condition){
            whereList.add( key + SqlKeyword.LE.getSqlSegment() + this.parseVal(val) );
        }
        return this;
    }

    /**
     * 不等于
     * @param key
     * @param val
     * @return
     */
    public QueryCondition ne(String key, Object val){
        this.ne(true,key,val);
        return this;
    }

    /**
     * 不等于
     * @param key
     * @param val
     * @return
     */
    public QueryCondition ne(boolean condition, String key, Object val){
        if(condition){
            whereList.add( key + SqlKeyword.NE.getSqlSegment() + this.parseVal( val)   );
        }
        return this;
    }

    /**
     * 包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition in(String key, Object... val){
        this.in(true,key,val);
        return this;
    }

    /**
     * 包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition in(boolean condition, String key, Object... val){
        if(condition){
            List<Object> paramsList = Arrays.asList(val);
            if(val[0] instanceof List){
                List list = (List) val[0];
                this.in( key, list );
            }
            else {
                this.in( key,paramsList);
            }

        }
        return this;
    }


    /**
     * 包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition in(String key, List<Object> val){
        this.in(true,key,val);
        return this;
    }

    /**
     * 包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition in(boolean condition, String key, List<Object> val){
        if(condition){
            String inParams = val.stream().map(this::parseVal).collect(Collectors.joining(",", "(", ")"));
            whereList.add( key + SqlKeyword.IN.getSqlSegment() +  inParams);
        }
        return this;
    }

    /**
     * 不包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition notIn(String key, Object... val){
        this.notIn(true,key,val);
        return this;
    }

    /**
     * 不包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition notIn(boolean condition, String key, Object... val){
        if(condition){
            List<Object> paramsList = Arrays.asList(val);
            if(val[0] instanceof List){
                List list = (List) val[0];
                this.notIn( key, list );
            }
            else {
                this.notIn( key,paramsList);
            }

        }
        return this;
    }


    /**
     * 不包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition notIn(String key, List<Object> val){
        this.notIn(true,key,val);
        return this;
    }

    /**
     * 不包含
     * @param key
     * @param val
     * @return
     */
    public QueryCondition notIn(boolean condition, String key, List<Object> val){
        if(condition){
            String inParams = val.stream().map(this::parseVal).collect(Collectors.joining(",", "(", ")"));
            whereList.add( key + SqlKeyword.NOT_IN.getSqlSegment() +  inParams);
        }
        return this;
    }

    /**
     * like
     * @param key
     * @param val
     * @return
     */
    public QueryCondition like(String key, String val){
        this.like(true,key,"%"+val+"%");
        return this;
    }

    /**
     * like
     * @param key
     * @param val
     */
    public void like(boolean condition,String key,String val){
        if(condition){
            whereList.add( key + SqlKeyword.LIKE.getSqlSegment() +val);
        }
    }

    /**
     * like
     * @param key
     * @param val
     * @return
     */
    public QueryCondition likeLeft(boolean condition, String key, String val){
        this.like(condition,key,val+"%");
        return this;
    }


    /**
     * like
     * @param key
     * @param val
     * @return
     */
    public QueryCondition likeLeft(String key, String val){
        this.like(true,key,val+"%");
        return this;
    }

    /**
     * like
     * @param key
     * @param val
     * @return
     */
    public QueryCondition likeRight(boolean condition, String key, String val){
        this.like(condition,key,val+"%");
        return this;
    }

    /**
     * like
     * @param key
     * @param val
     * @return
     */
    public QueryCondition likeRight(String key, String val){
        this.like(true,key,val+"%");
        return this;
    }

    /**
     * 升序
     * @param key
     * @return
     */
    public QueryCondition orderByAsc(String key){
        this.orderBy(true,key, SqlKeyword.ASC.getSqlSegment());
        return this;
    }

    /**
     * 升序
     * @param key
     * @return
     */
    public QueryCondition orderByAsc(boolean condition, String key){
        this.orderBy(condition,key, SqlKeyword.ASC.getSqlSegment());
        return this;
    }

    /**
     * 升序
     * @param key
     * @return
     */
    public QueryCondition orderByAsc(String... key){
        for (String field : key) {
            this.orderByAsc( field);
        }
        return this;
    }

    /**
     * 降序
     * @param key
     * @return
     */
    public QueryCondition orderByDesc(String... key){
        for (String field : key) {
            this.orderByDesc( field);
        }
        return this;
    }

    public QueryCondition orderBy(boolean condition, String key, String orderBy) {
        if(condition){
            this.orderByList.add( key + SqlKeyword.SPACE.getSqlSegment() + orderBy);
        }
        return this;

    }

    /**
     * 降序
     * @param key
     * @return
     */
    public QueryCondition orderByDesc(String key){
        this.orderBy(true,key, SqlKeyword.DESC.getSqlSegment());
        return this;
    }
    /**
     * 降序
     * @param key
     * @return
     */
    public QueryCondition orderByDesc(boolean condition, String key){
        this.orderBy(condition,key, SqlKeyword.DESC.getSqlSegment());
        return this;
    }

    /**
     * limit
     * @param limit
     */
    public QueryCondition limit(int limit){
        this.limit(true,limit);
        return this;
    }

    /**
     * limit
     * @param limit
     */
    public QueryCondition limit(boolean condition,int limit){
        if (condition) {
            this.limit = limit;
        }
        return this;
    }

    /**
     * or
     */
    public QueryCondition or(Consumer<QueryCondition> orConsumer) {

        QueryCondition qc = new QueryCondition("");
        orConsumer.accept( qc );
        List<String> qcWhereList = qc.whereList;
        if (! qcWhereList.isEmpty()) {
            this.orSql = " and (" + qcWhereList.stream().collect(Collectors.joining(SqlKeyword.OR.getSqlSegment() )) + ") ";
        }

        return this;
    }

    /**
     * page
     * @param page 1
     * @param limit 10
     */
    public QueryCondition page(int page,int limit){
        if( page < 1){
            throw new RuntimeException("page not lt 1");
        }

        // 新增限制
        if( MAX_PAGE_LIMIT < limit){
            limit = MAX_PAGE_LIMIT;
        }

        this.page( true,page,limit);
        return this;
    }

    /**
     * page
     * @param page
     * @param limit
     */
    public QueryCondition page(boolean condition,int page,int limit){
        if( condition){
            this.page = page;
            this.limit = limit;
        }
        return this;
    }

    /**
     * 解析值
     * @param val
     * @return
     */
    private String parseVal( Object val){

        // 日期处理
        if( val instanceof Date){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = dateFormat.format((Date) val);
            return this.strValue( format );
        }

        if(val instanceof LocalDate){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return this.strValue( ((LocalDate) val).format(formatter) );
        }

        if( val instanceof LocalDateTime){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return  this.strValue( ((LocalDateTime) val).format(formatter) );
        }

        return val instanceof String ? this.strValue( (String) val ) : JSON.toJSONString( val );
    }

    /**
     * 值Str化
     * @return
     */
    private String strValue(String str){
        return "'"+ str +"'";
    }

    /**
     * 获取查询 selectField
     */
    public String getSelectFieldSql(){
        return String.join(",", selectFieldList );
    }

    /**
     * 获取条件
     * @return
     */
    public String getWhereConditionSql(){
        return String.join( SqlKeyword.AND.getSqlSegment() ,whereList );
    }

    /**
     * 获取排序
     * @return
     */
    public String getOrderBySql(){
        return String.join(",",orderByList) ;
    }

    /**
     * 执行者  可以使用继承进行分离
     * @return
     */
    public QueryParam queryParam(){

        String whereConditionSql = this.getWhereConditionSql() + orSql;

        QueryParam queryParam = new QueryParam()
                .setFormId(formId)
                .setFieldKeys(this.getSelectFieldSql())
                .setFilterString( whereConditionSql )
                .setOrderString(this.getOrderBySql());

        if( page != null){

            // 开始行数  从第 0 行开始
            int startRow = (page - 1) * limit;
            int topRow = (page * limit) - 1;

            // 从第几行开始
            queryParam.setStartRow( startRow );
            // 到第几行结束
            queryParam.setTopRowCount( topRow );
        }

        if( limit != null){
            queryParam.setLimit( limit);
        }
        return queryParam;
    }

    /**
     * 清除查询字段
     * @return
     */
    public QueryCondition clearSelect(){
        this.selectFieldList.clear();
        return this;
    }
}