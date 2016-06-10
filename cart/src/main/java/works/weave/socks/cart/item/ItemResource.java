package works.weave.socks.cart.item;

import works.weave.socks.cart.cart.Resource;
import works.weave.socks.cart.entities.Item;
import works.weave.socks.cart.repositories.ItemRepository;

import java.util.Optional;
import java.util.function.Supplier;

public class ItemResource implements Resource<Item> {
    private final FoundItem<Item> foundItem;
    private final ItemRepository itemRepository;
    private final Supplier<Item> item;

    public ItemResource(ItemRepository itemRepository, Supplier<Item> item) {
        this.itemRepository = itemRepository;
        this.item = item;
        foundItem = new FoundItem<>(itemRepository, item.get().itemId);
    }

    @Override
    public Runnable destroy() {
        return () -> itemRepository.delete(value().get());
    }

    @Override
    public Runnable create() {
        return () -> itemRepository.save(item.get());
    }

    @Override
    public Supplier<Item> value() {
        return () -> Optional
                .of(foundItem.get())
                .orElseGet(() -> {
                    create().run();
                    return value().get();
                });
    }

    @Override
    public Runnable merge(Item toMerge) {
        return () -> itemRepository.save(new Item(foundItem.get(), toMerge.quantity));
    }
}
