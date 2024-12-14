import ge.tbcitacademy.data.Constants;
import org.testng.annotations.Factory;

public class FactoryExecutor {
    @Factory
    public Object[] factoryExecutor(){
        return new Object[]{
                new ParametrizedSwoopTests2(Constants.HOLIDAY_CATEGORY),
                new ParametrizedSwoopTests2(Constants.FUN_CATEGORY),
                new ParametrizedSwoopTests2(Constants.FOOD_CATEGORY),
        };
    }
}
