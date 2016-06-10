package works.weave.socks.cart.cart;

import works.weave.socks.cart.entities.Cart;

import java.util.*;

public interface CartDAO {
    void delete(Cart cart);

    void save(Cart cart);

    List<Cart> findByCustomerId(String customerId);

    class Fake implements CartDAO {
        private Map<String, Cart> cartStore = new HashMap<>();

        @Override
        public void delete(Cart cart) {
            cartStore.remove(cart.customerId);
        }

        @Override
        public void save(Cart cart) {
            cartStore.put(cart.customerId, cart);
        }

        @Override
        public List<Cart> findByCustomerId(String customerId) {
            Cart cart = cartStore.get(customerId);
            if (cart != null) {
                return Collections.singletonList(cart);
            } else {
                return Collections.emptyList();
            }
        }
    }
}
