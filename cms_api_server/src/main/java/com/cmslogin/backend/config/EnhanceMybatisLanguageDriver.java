package com.cmslogin.backend.config;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import lombok.SneakyThrows;

public class EnhanceMybatisLanguageDriver extends XMLLanguageDriver {
  public EnhanceMybatisLanguageDriver() {
  }

  @Override
  public ParameterHandler createParameterHandler(final MappedStatement mappedStatement, final Object parameterObject,
      final BoundSql boundSql) {
    addDebuggingComment(boundSql);
    return super.createParameterHandler(mappedStatement, parameterObject, boundSql);
  }

  @Override
  public SqlSource createSqlSource(final Configuration configuration, final XNode script,
      final Class<?> parameterType) {
    return super.createSqlSource(configuration, script, parameterType);
  }

  @Override
  public SqlSource createSqlSource(final Configuration configuration, final String script,
      final Class<?> parameterType) {
    return super.createSqlSource(configuration, script, parameterType);
  }

  @SneakyThrows
  private void addDebuggingComment(final BoundSql boundSql) {
    final Field sqlField = BoundSql.class.getDeclaredField("sql");
    sqlField.setAccessible(true);

    String sql = (String) sqlField.get(boundSql);
    final List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    sql = addParameterComment(sql, parameterMappings);

    sqlField.set(boundSql, sql);
  }

  private String addParameterComment(final String sql, final List<ParameterMapping> parameters) {
    final StringBuilder sqlInternalStringBuilder = new StringBuilder(sql);

    int paramReverseIndex = parameters.size() - 1;
    for (int idx = sql.length() - 1; idx > 0; idx--) {
      final char c = sql.charAt(idx);
      if (c == '?') {
        final String commentedString = toCommentString(parameters.get(paramReverseIndex).getProperty());

        sqlInternalStringBuilder.insert(idx + 1, commentedString);
        paramReverseIndex = paramReverseIndex - 1;
      }
    }

    return sqlInternalStringBuilder.toString();
  }

  private String toCommentString(final String comment) {
    return " /*" + comment + "*/ ";
  }

}