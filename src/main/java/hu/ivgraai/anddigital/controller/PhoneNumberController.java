package hu.ivgraai.anddigital.controller;

import hu.ivgraai.anddigital.dao.ImagineryDatabase;
import hu.ivgraai.anddigital.dto.PhoneNumber;
import hu.ivgraai.anddigital.exception.BusinessException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gergo Ivan
 */
@RestController
public class PhoneNumberController {

    @Autowired
    private ImagineryDatabase db;

    private static final RuntimeException ERROR = new BusinessException();
    private static final BiConsumer<Map<String, List<PhoneNumber>>, String> CHECKER = (collection, element) -> {
            if (null == element || !collection.containsKey(element)) {
                throw ERROR;
            }
        };

    @RequestMapping(value = "/phone_numbers", method = RequestMethod.GET)
    public List<PhoneNumber> getAllPhoneNumbers() {
        return db.selectAllQuery()
                .entrySet()
                .stream()
                .flatMap(pair -> pair.getValue().stream())
                .collect(toList());
    }

    @RequestMapping(value = "/phone_number", method = RequestMethod.GET)
    public List<PhoneNumber> getAllPhoneNumbersOfASingleCustomer(@RequestParam(value = "customer") String customer) {
        Map<String, List<PhoneNumber>> resultSet = db.selectAllQuery();
        CHECKER.accept(resultSet, customer);
        return resultSet.get(customer);
    }

    @RequestMapping(value = "/activate_number", method = RequestMethod.PUT)
    public void activateAPhoneNumber(@RequestParam(value = "customer") String customer, @RequestParam(value = "number") String number) {
        // TODO: Make swagger until here it is a command 'curl -X PUT "http://localhost:8080/activate_number?customer=IVGRAAI&number=%2B36%2012%20345%206789"'
        Map<String, List<PhoneNumber>> resultSet = db.selectAllQuery();
        CHECKER.accept(resultSet, customer);
        Optional<PhoneNumber> optional = resultSet.get(customer)
                .stream()
                .filter(phoneNumber -> number.equals(phoneNumber.getValue()))
                .findAny();
        if (!optional.isPresent()) {
            throw ERROR;
        }
        optional.get().setActive(Boolean.TRUE);
    }

}
