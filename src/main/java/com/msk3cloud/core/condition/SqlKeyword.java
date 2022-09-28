package com.msk3cloud.core.condition;

/**
 * @author Rao
 * @Date 2022/01/12
 **/
public enum SqlKeyword implements ISqlSegment {
    AND(" AND "),
    OR(" OR "),
    IN(" IN "),
    NOT_IN(" NOT IN "),
    NOT(" NOT "),
    LIKE(" LIKE "),
    EQ(" = "),
    NE(" <> "),
    GT(" > "),
    GE(" >= "),
    LT(" < "),
    LE(" <= "),
    IS_NULL(" IS NULL "),
    IS_NOT_NULL(" IS NOT NULL "),
    GROUP_BY(" GROUP BY "),
    HAVING(" HAVING "),
    ORDER_BY(" ORDER BY "),
    EXISTS(" EXISTS "),
    BETWEEN(" BETWEEN "),
    ASC(" ASC "),
    DESC(" DESC "),
    SPACE(" "),
    ;
    private final String keyword;

    SqlKeyword(final String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getSqlSegment() {
        return this.keyword;
    }
}

