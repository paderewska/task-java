package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloControllerTest {

    @InjectMocks
    private TrelloController trelloController;

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    public void getTrelloBoards() {

        //Given
        TrelloListDto trelloListDto = new TrelloListDto("444", "kominiarz", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("123", "nazwa", trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtoList);

        //When
        List<TrelloBoardDto> test = trelloController.getTrelloBoards();

        //Then
        assertEquals(1, test.size());

        trelloBoardDtoList.forEach(trelloBoardDto1 -> {
            assertEquals("123", trelloBoardDto1.getId());
            assertEquals("nazwa", trelloBoardDto1.getName());

            trelloBoardDto1.getLists().forEach(trelloListDto1 -> {
                assertEquals("444", trelloListDto1.getId());
                assertEquals("kominiarz", trelloListDto1.getName());
                assertEquals(true, trelloListDto1.isClosed());
            });
        });
    }

    @Test
    public void createdTrelloCard() {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Zakupy", "Ziemniaki na frytki", "pos", "listaId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Zakupy", "www.yo.pl");
        when(trelloFacade.createdCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto test = trelloController.createdTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", test.getId());
        assertEquals("Zakupy", test.getName());
        assertEquals("www.yo.pl", test.getShortUrl());
    }
}