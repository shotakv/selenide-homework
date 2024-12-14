package ge.tbcitacademy.data;

import org.testng.annotations.DataProvider;

public class DataSupplier {
    @DataProvider(name = "offersData")
    public Object[][] offers(){
        return new Object[][]{
                {"ასტრა პარკი | Astra Park"},
                {"ლისი ლემანსი | Lisi Lemans"},
                {"World Fitness"},
                {"კოლიზეუმ ჯიმ |Colloseum Gym"},
                {"დიელ ფიტნესი | DL fitness"},
                {"მანდალა სამგორის ფილიალი | Mandala"},
                {"რანჩო პალომინო | Palomino Ranch"},
                {"არენა II | Arena II Sports Complex"},
                {"სკინანე | Skinane"},
                {"ჰორიზონტი | Horizont"}
        };
    }

    @DataProvider
    public Object[][] studentsData(){
        return new Object[][]{
                {"Bruce","Wayne","Male","5920400700"},
                {"Optimus","Prime","Other","5981241155"},
                {"Mayuri","Shiina","Female","5999999999"}
        };
    }

}
