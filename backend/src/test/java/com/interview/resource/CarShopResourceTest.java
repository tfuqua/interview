package com.interview.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.dto.CarShopDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DisplayName("CarShop resource Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CarShopResourceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/carshops";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Validate integration test configuration")
    public void testSpringBootContextIsInitialized() {
        // when
        ServletContext servletContext = webApplicationContext.getServletContext();
        // then
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("carShopResource"));
    }

    @ParameterizedTest
    @MethodSource("createCarShopValidRequests")
    @DisplayName("Creating a CarShop with valid details should work")
    public void testCreatingACarShopWithValidDetails(CarShopDTO requestBody) throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(requestBody)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));

    }

    @ParameterizedTest
    @MethodSource("createCarShopInvalidRequests")
    @DisplayName("Creating a CarShop with invalid details should return HTTP_STATUS.BAD_REQUEST")
    public void testCreatingACarShopWithInvalidDetails(CarShopDTO requestBody) throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(requestBody)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Creating a CarShop with the same name twice should not work")
    public void testCreatingCarShopWithSameNameMultipleTimesShouldNotWork() throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        // when
        ResultActions firstRequest = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        ResultActions secondRequestWithSameName = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        // then
        firstRequest
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
        secondRequestWithSameName
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Retrieving an existing CarShop should work")
    public void testRetrievingAnExistingCarShopShouldWork() throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        // when
        ResultActions createRequest = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        String createdLocation = extractLocationHeader(createRequest);

        ResultActions getRequest = mockMvc.perform(
                get(createdLocation));

        // then
        createRequest
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
        getRequest
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(carShopDTO.getName()))
                .andExpect(jsonPath("$.description").value(carShopDTO.getDescription()))
                .andExpect(jsonPath("$.summary").value(carShopDTO.getSummary()))
                .andExpect(jsonPath("$.logoUrl").value(carShopDTO.getLogoUrl()));

    }

    @Test
    @DisplayName("Deleting an existing CarShop should work")
    public void testDeletingAnExistingCarShopShouldWork() throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        // when
        ResultActions createRequest = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        String createdLocation = extractLocationHeader(createRequest);

        ResultActions deleteRequest = mockMvc.perform(
                delete(createdLocation));

        // then
        createRequest
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
        deleteRequest
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Deleting a non existing CarShop should not work")
    public void testDeletingANonExistingCarShopShouldWork() throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        // when
        ResultActions deleteRequest = mockMvc.perform(
                delete(BASE_PATH + "/9999"));

        // then
        deleteRequest
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Updating an existing CarShop should work")
    public void testUpdatingAnExistingCarShopShouldWork() throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        CarShopDTO updateCarShopDto = givenAValidCarShopRequest();
        updateCarShopDto.setName("UpdatedName");
        // when
        ResultActions createRequest = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        String createdLocation = extractLocationHeader(createRequest);

        ResultActions updateRequest = mockMvc.perform(
                put(createdLocation)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(updateCarShopDto)))
                .andDo(print());

        // then
        createRequest
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
        updateRequest
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateCarShopDto.getName()))
                .andExpect(jsonPath("$.description").value(carShopDTO.getDescription()))
                .andExpect(jsonPath("$.summary").value(carShopDTO.getSummary()))
                .andExpect(jsonPath("$.logoUrl").value(carShopDTO.getLogoUrl()));

    }

    @ParameterizedTest
    @MethodSource("createCarShopInvalidRequests")
    @DisplayName("Updating an invalid or non existing CarShop should not work")
    public void testUpdatingAnNonExistingOrInvalidCarShopShouldWork(CarShopDTO updateCarShopDto) throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        // when
        ResultActions createRequest = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        String createdLocation = extractLocationHeader(createRequest);

        ResultActions updateRequest = mockMvc.perform(
                put(createdLocation)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(updateCarShopDto)))
                .andDo(print());

        // then
        createRequest
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
        updateRequest
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Retrieving all existing CarShop should work")
    public void testRetrievingAllExistingCarShopShouldWork() throws Exception {
        // given
        CarShopDTO carShopDTO = givenAValidCarShopRequest();
        // when
        ResultActions createRequest = mockMvc.perform(
                post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson(carShopDTO)))
                .andDo(print());

        ResultActions getRequest = mockMvc.perform(
                get(BASE_PATH));

        // then
        createRequest
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
        getRequest
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(carShopDTO.getName()))
                .andExpect(jsonPath("$.content[0].logoUrl").value(carShopDTO.getLogoUrl()))
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].description").doesNotExist())
                .andExpect(jsonPath("$.content[0].summary").doesNotExist());

    }

    private CarShopDTO givenAValidCarShopRequest() {
        return new CarShopDTO.CarShopDTOBuilder("CarWow")
                .withDescription("CarWow is the number one car shop in the UK, with a large variety of certified dealers")
                .withSummary("CarWow.co.uk Uk's no.1 car dealer")
                .withLogoUrl("https://carshow.co.uk/logo.png")
                .build();
    }

    private String extractLocationHeader(ResultActions createRequest) {
        String location = createRequest.andReturn().getResponse().getHeader("Location");
        assertNotNull(location);
        return location;
    }

    // This method might be marked as unused by your IDE but it's currently so due to an Intellij bug, that's prohibiting
    // @MethodSource to be correctly linked to the underlying provider method
    private static Stream<Arguments> createCarShopValidRequests() {
        return Stream.of(
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu").build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithDescription").withDescription("Description").build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithLongDescription").withDescription("Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly20CharactersL").build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithSummary").withSummary("Summary").build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithLongSummary")
                        .withSummary("Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsuExactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu")
                        .build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithLogo").withLogoUrl("https://logo.com/logo.png").build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShop").build())
        );
    }

    // This method might be marked as unused by your IDE but it's currently so due to an Intellij bug, that's prohibiting
    // @MethodSource to be correctly linked to the underlying provider method
    private static Stream<Arguments> createCarShopInvalidRequests() {
        return Stream.of(
                Arguments.of(new CarShopDTO.CarShopDTOBuilder(null).build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithLongDescription").withDescription("Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsu" +
                        "StringThatShouldPushDescriptionOverTheLimit").build()),
                Arguments.of(new CarShopDTO.CarShopDTOBuilder("CarShopWithLongSummary")
                        .withSummary("Exactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsuExactly60CharactersLongStringLoremImpsumLoremImpsumLoremIpsum")
                        .build())
        );
    }

    private String dtoToJson(CarShopDTO dto) throws JsonProcessingException {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);
    }
}