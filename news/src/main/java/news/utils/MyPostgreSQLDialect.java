package news.utils;

import org.hibernate.dialect.PostgreSQLDialect;

public class MyPostgreSQLDialect extends PostgreSQLDialect {
public MyPostgreSQLDialect() {
   registerFunction("fts", new PostgreSQLFullTextSearchFunction());
}
}