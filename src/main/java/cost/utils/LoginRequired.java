package cost.utils;

import java.lang.annotation.*;

/**
 * Created by hongnan.zhao on 2015/12/14.
 */
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
