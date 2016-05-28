package news.utils;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class PostgreSQLFullTextSearchFunction implements SQLFunction {

   @SuppressWarnings("unchecked")
   public String render(List args, SessionFactoryImplementor factory) {
      if (args.size() != 3) {
         throw new IllegalArgumentException(
               "The function must be passed 3 arguments");
      }

      String ftsConfig = (String) args.get(0);
      String field = (String) args.get(1);
      String value = (String) args.get(2);

      String fragment = "fts @@ " + "to_tsquery(" + value + ")";
      return fragment;

   }

   @Override
   public Type getReturnType(Type columnType, Mapping mapping)
         throws QueryException {
      return new BooleanType();
   }

   @Override
   public boolean hasArguments() {
      return true;
   }

   @Override
   public boolean hasParenthesesIfNoArguments() {
      return false;
   }
}
