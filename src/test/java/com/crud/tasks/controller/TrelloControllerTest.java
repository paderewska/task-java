package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;


    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {

        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().is(200))
              .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {

        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "Test list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "Test Task", trelloLists));

        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Trello board fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test Task")))
                // Trello list fields
        .andExpect(jsonPath("$[0].lists", hasSize(1)))
        .andExpect(jsonPath("$[0].lists[0].id", is("1")))
        .andExpect(jsonPath("$[0].lists[0].name", is("Test list")))
        .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test",
                "Test description",
                "top",
                "1");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "323",
                "Test",
                "http://test.com");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        when(trelloFacade.createdCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);

        //When & Then
        mockMvc.perform(post("/v1/trello/createTrelloCard")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("323")))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.shortUrl", is("http://test.com")));
    }
}

//import com.crud.tasks.domain.CreatedTrelloCardDto;
//import com.crud.tasks.domain.TrelloBoardDto;
//import com.crud.tasks.domain.TrelloCardDto;
//import com.crud.tasks.domain.TrelloListDto;
//import com.crud.tasks.trello.facade.TrelloFacade;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TrelloControllerTest {
//
//    @InjectMocks
//    private TrelloController trelloController;
//
//    @Mock
//    private TrelloFacade trelloFacade;
//
//    @Test
//    public void getTrelloBoards() {
//
//        //Given
//        TrelloListDto trelloListDto = new TrelloListDto("444", "kominiarz", true);
//        List<TrelloListDto> trelloListDtos = new ArrayList<>();
//        trelloListDtos.add(trelloListDto);
//
//        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("123", "nazwa", trelloListDtos);
//        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
//        trelloBoardDtoList.add(trelloBoardDto);
//        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtoList);
//
//        //When
//        List<TrelloBoardDto> test = trelloController.getTrelloBoards();
//
//        //Then
//        assertEquals(1, test.size());
//
//        trelloBoardDtoList.forEach(trelloBoardDto1 -> {
//            assertEquals("123", trelloBoardDto1.getId());
//            assertEquals("nazwa", trelloBoardDto1.getName());
//
//            trelloBoardDto1.getLists().forEach(trelloListDto1 -> {
//                assertEquals("444", trelloListDto1.getId());
//                assertEquals("kominiarz", trelloListDto1.getName());
//                assertEquals(true, trelloListDto1.isClosed());
//            });
//        });
//    }
//
//    @Test
//    public void createdTrelloCard() {
//
//        //Given
//        TrelloCardDto trelloCardDto = new TrelloCardDto("Zakupy", "Ziemniaki na frytki", "pos", "listaId");
//        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Zakupy", "www.yo.pl");
//        when(trelloFacade.createdCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
//
//        //When
//        CreatedTrelloCardDto test = trelloController.createdTrelloCard(trelloCardDto);
//
//        //Then
//        assertEquals("1", test.getId());
//        assertEquals("Zakupy", test.getName());
//        assertEquals("www.yo.pl", test.getShortUrl());
//    }
//}