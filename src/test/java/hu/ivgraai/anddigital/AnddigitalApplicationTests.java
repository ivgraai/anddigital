package hu.ivgraai.anddigital;

import hu.ivgraai.anddigital.dao.ImagineryDatabase;
import hu.ivgraai.anddigital.dto.PhoneNumber;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author Gergo Ivan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = WebEnvironment.RANDOM_PORT*/)
@AutoConfigureMockMvc
public class AnddigitalApplicationTests {

    private static final Map<String, List<PhoneNumber>> RETURN_VALUE = new HashMap<String, List<PhoneNumber>>() {{
        put("Winch Ester", Arrays.asList(
                new PhoneNumber("dummy1", true),
                new PhoneNumber("dummy2", false)
        ));
    }};
    private static final String JSON_VALUE = "[{\"value\":\"dummy1\",\"active\":true},{\"value\":\"dummy2\",\"active\":false}]";

    @MockBean
    private ImagineryDatabase imagineryDb;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        given(imagineryDb.selectAllQuery()).willReturn(RETURN_VALUE);
    }

    @Test
    public void complexScenario() throws Exception {
        mockMvc.perform(put("/activate_number").param("customer", "Winch Ester").param("number", "dummy2"));
        mockMvc.perform(get("/phone_number").param("customer", "Winch Ester")).andExpect(status().isOk())
                .andExpect(jsonPath("$[1].active").value(true));
    }

    @Test
    public void trivialScenario() throws Exception {
        MvcResult result = mockMvc.perform(get("/phone_numbers")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertEquals(JSON_VALUE, content);
    }

    @Test
    public void incorrectScenario() throws Exception {
        mockMvc.perform(get("/phone_number").param("customer", "dummy")).andExpect(status().isBadRequest());
    }

}
