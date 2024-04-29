package de.kaluza.trades.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaluza.generated.model.GetTradesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerTradesRestControllerCT {

    private static final String HEADER_CUSTOMER_ID = "customerId";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTrades_noTrades() throws Exception {
        // given
        // add no trades

        // when
        final var response =
                mockMvc
                        .perform(
                                get("/trades")
                                        .header(HEADER_CUSTOMER_ID, HEADER_CUSTOMER_ID)
                                        .accept(MediaType.APPLICATION_JSON))

                        // then
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse().getContentAsString();

        final var result = objectMapper.readValue(response, GetTradesResponse.class);

        assertThat(result.getContent()).isEmpty();
    }

}
