package be.cegeka.devopscourse.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BingoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void givenNewItem_whenSaveItem_thenPersistInDb() throws Exception {
        Item newItem = new Item(randomUUID().toString(), "someAuthor", "It works on my machine.");

        mockMvc.perform(
                put("/bingo/items")
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/bingo/item/" + newItem.getId()));
        Item persistedItem = itemRepository.findOne(newItem.getId());

        assertThat(persistedItem).isEqualTo(newItem);
    }

    @Test
    public void givenExistingItems_whenGetItems_thenReturnAllItems() throws Exception {
        Item item1 = new Item(randomUUID().toString(), "someAuthor", "It works on my machine.");
        Item item2 = new Item(randomUUID().toString(), "someOtherAuthor", "We should have done it ourselves inSTAID...");
        itemRepository.save(item1);
        itemRepository.save(item2);

        mockMvc.perform(get("/bingo/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json(objectMapper.writeValueAsString(asList(item1, item2))));
    }

}
