package be.cegeka.devopscourse.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/bingo")
public class BingoController {

    @Autowired
    private ItemService itemService;

    @PutMapping(value = "/items", consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addItem(@RequestBody Item newItem) {
        itemService.saveItem(newItem);
        URI uri = UriComponentsBuilder.fromPath("/bingo/item/" + newItem.getId())
                .build()
                .encode()
                .toUri();
        return created(uri).build();
    }

    @GetMapping(value = "/items", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getItems() {
        return ok().body(itemService.findAllItems());
    }

}
