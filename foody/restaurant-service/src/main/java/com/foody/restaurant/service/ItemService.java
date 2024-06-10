package com.foody.restaurant.service;

import com.foody.common.mapper.ItemMapper;
import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.misc.Item;
import com.foody.data.repository.restaurant.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public Mono<List<ItemResponse>> saveItems(List<ItemRequest> itemRequests) {
        List<Item> items = itemRequests.stream()
                .map(itemMapper::convertToItem)
                .toList();

        return itemRepository.saveAll(items).map(itemMapper::convertToMenuItemResponse).collectList();

    }

    public Mono<Boolean> updateItem(String menuId, ItemRequest menuItemRequest) {
        return itemRepository.findById(menuId)
                .flatMap(existingMenu -> {
                    Item updatedMenu = itemMapper.convertToItem(menuItemRequest);
                    //TODO: update the field values which we get request
                    return itemRepository.updateItem(updatedMenu);
                });
    }

    public Mono<ItemResponse> getItemById(String itemId) {
        return itemRepository.findById(itemId).map(itemMapper::convertToMenuItemResponse);
    }

    public Mono<Void> deleteItem(String menuId) {
        return itemRepository.deleteById(menuId);
    }

    public Flux<ItemResponse> getAllItems(Integer page, Integer size, String sortByField) {
        return itemRepository.findAll(page, size, sortByField).map(itemMapper::convertToMenuItemResponse);
    }
}
