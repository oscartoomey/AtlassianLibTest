package com.example.specvalidationtest;
import com.example.specvalidationtest.controllers.TestController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpecValidationMockMvcTest {

    private static final String SPEC_PATH = "time-to-pay-v1.0.0.yaml";

    @Test
    public void testGetPlan() throws Exception {
        final TestController testController = new TestController();
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testController).build();

        mockMvc.perform(get("/debts/time-to-pay/quote/uniqRef1234/100")
                .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(SPEC_PATH));
    }

}
