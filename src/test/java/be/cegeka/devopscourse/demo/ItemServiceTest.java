package be.cegeka.devopscourse.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void givenItem_whenSaveItem_thenPassItemToRepository() throws Exception {
        Item item = new Item();

        itemService.saveItem(item);

        verify(itemRepository).save(item);
    }

    @Test
    public void whenFindAllItems_thenReturnAllItemsFromRepository() throws Exception {
        List<Item> items = emptyList();
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> allItems = itemService.findAllItems();

        assertThat(allItems).isEqualTo(items);
    }

}