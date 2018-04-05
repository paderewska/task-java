package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoards() {

        //Given
        List<TrelloListDto> listListDto = new ArrayList<>();
        TrelloListDto listDto = new TrelloListDto("123", "zakupy", false);
        listListDto.add(listDto);

        List<TrelloBoardDto> listBoardDto = new ArrayList<>();
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "nazwa", listListDto);
        listBoardDto.add(boardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(listBoardDto);

        //When
        List<TrelloBoardDto> test = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(test);
        assertEquals(1, test.size());

        test.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("nazwa", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("123", trelloListDto.getId());
                assertEquals("zakupy", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void createdTrelloCard() {

        //Given
        TrelloCardDto cardDto = new TrelloCardDto("nowa karta", "Opis karty", "pos", "listId");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto("1", "nowa karta", "www.karta.pl");
        when(trelloClient.createNewCard(cardDto)).thenReturn(newCard);

        //When
        CreatedTrelloCardDto test = trelloService.createdTrelloCard(cardDto);

        //Then
        assertEquals("1", test.getId());
        assertEquals("nowa karta", test.getName());
        assertEquals("www.karta.pl", test.getShortUrl());
    }
}