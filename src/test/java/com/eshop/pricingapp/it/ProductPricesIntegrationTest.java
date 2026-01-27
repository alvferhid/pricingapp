package com.eshop.pricingapp.it;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductPricesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("When retrieving all products then returns full list")
    @Test
    void whenRetrievingAllProductsThenReturnsFullList() throws Exception {
        //WHEN - THEN

        mockMvc.perform(get("/product/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].price").value(35.50))
                .andExpect(jsonPath("$[1].price").value(25.45))
                .andExpect(jsonPath("$[2].price").value(30.5))
                .andExpect(jsonPath("$[3].price").value(38.95));
    }

    @DisplayName("When retrieving with parameters from test 1 should return correct data")
    @Test
    void testOne() throws Exception {
        //GIVEN
        String date = "2020-06-14T10:00:00";
        String productId = "35455";
        String brandId = "1";

        //WHEN - THEN
        mockMvc.perform(get("/product/offer/" + date + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.5));
    }

    @DisplayName("When retrieving with parameters from test 2 should return correct data")
    @Test
    void testTwo() throws Exception {
        //GIVEN
        String date = "2020-06-14T16:00:00";
        String productId = "35455";
        String brandId = "1";

        //WHEN - THEN
        mockMvc.perform(get("/product/offer/" + date + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @DisplayName("When retrieving with parameters from test 3 should return correct data")
    @Test
    void testThree() throws Exception {
        //GIVEN
        String date = "2020-06-14T21:00:00";
        String productId = "35455";
        String brandId = "1";

        //WHEN - THEN
        mockMvc.perform(get("/product/offer/" + date + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.5));
    }

    @DisplayName("When retrieving with parameters from test 4 should return correct data")
    @Test
    void testFour() throws Exception {
        //GIVEN
        String date = "2020-06-15T10:00:00";
        String productId = "35455";
        String brandId = "1";

        //WHEN - THEN
        mockMvc.perform(get("/product/offer/" + date + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.5));
    }

    @DisplayName("When retrieving with parameters from test 5 should return correct data")
    @Test
    void testFive() throws Exception {
        //GIVEN
        String date = "2020-06-16T21:00:00";
        String productId = "35455";
        String brandId = "1";

        //WHEN - THEN
        mockMvc.perform(get("/product/offer/" + date + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("When invalid parameters are provided then returns BAD_REQUEST")
    void whenInvalidParametersProvidedThenReturnsBadRequest() throws Exception {

        //GIVEN
        String invalidDate = "invalid-date";
        String productId = "0";
        String brandId = "0";

        //WHEN-THEN
        mockMvc.perform(get("/product/offer/" + invalidDate + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When looking for non existing data returns not found")
    void whenLookingForNonExisingDataReturnsNotFound() throws Exception {
        //GIVEN
        String invalidDate = "2029-06-16T21:00:00";
        String productId = "2323";
        String brandId = "2323";

        //WHEN-THEN
        mockMvc.perform(get("/product/offer/" + invalidDate + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("When applicationDate has invalid format then returns BAD_REQUEST with error body")
    void whenApplicationDateHasInvalidFormatThenReturnsBadRequest() throws Exception {

        // GIVEN
        String invalidDate = "2020-06-14-10:00:00"; // no ISO-8601
        String productId = "35455";
        String brandId = "1";

        // WHEN - THEN
        mockMvc.perform(get("/product/offer/" + invalidDate + "/" + productId + "/" + brandId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_DATE_FORMAT"))
                .andExpect(jsonPath("$.message")
                        .value("applicationDate must follow ISO-8601 format"));
    }

}
