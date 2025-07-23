package ru.yandex.practicum.shoppingcart;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.enums.State;
import ru.yandex.practicum.product.Product;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "shopping_carts")
@ToString
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID shoppingCartId;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private State shoppingCartState;

    @OneToMany(mappedBy = "shoppingCartId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Product> products;
}
