package hu.ivgraai.anddigital.dao;

import hu.ivgraai.anddigital.dto.PhoneNumber;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author Gergo Ivan
 */
@Component
// @Scope("singleton")
public class ImagineryDatabase {

    private static final Map<String, List<PhoneNumber>> TABLE;

    static {
        Map<String, List<PhoneNumber>> temp = new HashMap<>();
        temp.put("IVGRAAI", Arrays.asList(
                new PhoneNumber("+44 1234 567890", true),
                new PhoneNumber("+36 12 345 6789", false)
            ));
        temp.put("Gipsz Jakab", Arrays.asList(
                new PhoneNumber("+44 0987 654321", false)
            ));
        TABLE = Collections.unmodifiableMap(temp);
    }

    public Map<String, List<PhoneNumber>> selectAllQuery() {
        return TABLE;
    }

}
