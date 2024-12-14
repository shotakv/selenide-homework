
import ge.tbcitacademy.data.Constants;
import ge.tbcitacademy.data.DataSupplier;
import ge.tbcitacademy.util.BaseTest;
import ge.tbcitacademy.util.Helper;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class AlternativeParametrizations extends BaseTest {
    @Test
    @Parameters({"firstName","lastName","gender","mobileNumber"})
    public void validateWithParameters(String firstName,String lastName, String gender, String mobileNumber){
        open(Constants.DEMO_QA_PRACTICE_FORM);

        String tableStudentName = Helper.fillOutFormAndBringStudentName(firstName,lastName,gender,mobileNumber);
        String preMadeStudentName = firstName + " " + lastName;

        Assert.assertEquals(tableStudentName,preMadeStudentName);
    }

    @Test(dataProvider = "studentsData",dataProviderClass = DataSupplier.class)
    public void validateWithDataProvider(String firstName,String lastName, String gender, String mobileNumber){
        open(Constants.DEMO_QA_PRACTICE_FORM);

        String tableStudentName = Helper.fillOutFormAndBringStudentName(firstName,lastName,gender,mobileNumber);
        String preMadeStudentName = firstName + " " + lastName;

        Assert.assertEquals(tableStudentName,preMadeStudentName);
    }

}
