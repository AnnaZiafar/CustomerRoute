package demo.customerroute.tier;

import demo.customerroute.exception.TierAlreadyExistException;
import demo.customerroute.exception.TierNotFoundException;
import demo.customerroute.tier.application.TierService;
import demo.customerroute.tier.domain.Tier;
import demo.customerroute.tier.web.TierController;
import demo.customerroute.tier.web.TierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TierController.class)
class TierControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TierService service;

    private static final String URL = "/tiers/api";
    private static final String TIER = "diamond";
    private static final int DISCOUNT = 40;

    private TierDto tierDto;
    private Tier savedTier;

    @BeforeEach
    void setup(){
        savedTier = Tier.createNew(TIER, DISCOUNT);
        tierDto = new TierDto(TIER, DISCOUNT);
    }

    @Test
    void getMappingReturnsTierApi() throws Exception {
        when(service.findAll()).thenReturn(List.of(savedTier));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].level").value(TIER))
                .andExpect(jsonPath("$[0].discountPercentage").value(DISCOUNT));
    }

    @Test
    void postMappingAddsNewTier() throws Exception {
        when(service.addNewTier(tierDto)).thenReturn(savedTier);

        performAction(post(URL), tierDto, status().isOk())
                .andExpect(jsonPath("$.level").value(TIER))
                .andExpect(jsonPath("$.discountPercentage").value(DISCOUNT));

    }

    @Test
    void failToAddIfTierAlreadyExist() throws Exception {
        when(service.addNewTier(tierDto)).thenThrow(TierAlreadyExistException.class);

        performAction(post(URL), tierDto, status().isConflict());
    }


    @Test
    void putMappingUpdatesTier() throws Exception {
        int newDiscount = DISCOUNT + 1;
        TierDto updateDto = new TierDto(TIER, newDiscount);
        Tier updatedTier = Tier.createNew(TIER, newDiscount);

        when(service.updateTier(updateDto)).thenReturn(updatedTier);

        performAction(put(URL), updateDto, status().isOk())
                .andExpect(jsonPath("$.level").value(TIER))
                .andExpect(jsonPath("$.discountPercentage").value(newDiscount));
    }

    @Test
    void failToUpdateIfTierDoesNotExist() throws Exception {
        String level = TIER + "s";
        TierDto updateDto = new TierDto(level, DISCOUNT);

        when(service.updateTier(updateDto)).thenThrow(TierNotFoundException.class);

        performAction(put(URL), updateDto, status().isNotFound());
    }


    /**
     * Helper method to perform a MockMvc request with a JSON payload and verify the initial response status.
     *
     * @param requestBuilder  the request to execute (e.g., post, put)
     * @param dto             the data transfer object to be serialized into the request body
     * @param expectedStatus  the ResultMatcher for the expected HTTP status
     * @return the ResultActions to allow for further assertions (e.g., jsonPath)
     * @throws Exception if the request execution fails
     */
    private ResultActions performAction(MockHttpServletRequestBuilder requestBuilder, TierDto dto, ResultMatcher expectedStatus) throws Exception {
        return mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(expectedStatus);
    }

}
