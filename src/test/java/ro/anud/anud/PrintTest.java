package ro.anud.anud;


import org.junit.Ignore;
import org.junit.Test;

import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class PrintTest {

    @Test
    @Ignore
    public void test() {
        List<String> list = List.of("Liviu", "Vântu", "Dominik", "Parasca", "Drahoslav", "Simeonescu", "Laurensiu",
                                    "Barnutiu", "Danus", "Olaru", "Gabriel", "Suciu", "Traian", "Cernat", "Catalin",
                                    "Eliade", "Flaviu", "Popescu", "Glad", "Balauru", "Adam", "Nicu", "Skender",
                                    "Pirvu", "Dumitru", "Niculescu", "Cristinel", "Ragar", "Gabriel", "Lupei", "Costel",
                                    "Bengescu", "Dragos", "Ionescu", "Danut", "Ciumak", "Miron", "Cernat", "Andrei",
                                    "Vladimirescu", "Eugen", "Kinczllers", "Vasile", "Cosmescu", "Cosmin", "Mihai",
                                    "Valerian", "Tarus", "Skender", "Apostol", "Toma", "Rebreanu", "Tudor", "Pop",
                                    "Codrin", "Popescu", "Teodosie", "Pavel", "Ciodaru", "Apostol", "Jan", "Ilica",
                                    "Petre", "Constantinescu", "Danus", "Pogonat", "Dinu", "Gherea", "Danut",
                                    "Moculescu", "Abel", "Vladimirescu", "Flavius", "Ianculescu", "Marin", "Ardelean",
                                    "Dracul", "Ene", "Gabi", "Negutesco");

        var randomName = new RandomString(list, 2, ValueRange.of(4, 8), new Random());

        Stream.generate(() -> "")
                .limit(10)
                .forEach(s -> {
                    var firstName = randomName.generate();
                    var lastName = randomName.generate();
                    System.out.println(new StringBuffer().append(firstName.substring(0, 1).toUpperCase())
                                               .append(firstName.substring(1))
                                               .append(" ").append(lastName.substring(0, 1).toUpperCase())
                                               .append(lastName.substring(1)));
                });


    }
}
