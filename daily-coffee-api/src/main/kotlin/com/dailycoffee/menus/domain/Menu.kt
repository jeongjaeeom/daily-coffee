package com.dailycoffee.menus.domain

import com.dailycoffee.utils.IdGenerator
import java.math.BigDecimal
import java.util.UUID

class Menu(
    val id: UUID,
    val name: String,
    price: Price = Price.ZERO,
    val displayed: Boolean,
    val menuGroupId: UUID,
    val menuProducts: List<MenuProduct>,
) {
    var price: Price = price
        private set

    init {
        require(menuProducts.isNotEmpty())
        require(totalPrice() >= price)
    }

    constructor(
        name: String,
        price: BigDecimal,
        displayed: Boolean,
        menuGroupId: UUID,
        menuProducts: List<MenuProduct>,
    ) : this(
        IdGenerator.createId(),
        name,
        Price(price),
        displayed,
        menuGroupId,
        menuProducts,
    )

    private fun totalPrice(): Price = menuProducts.stream()
        .map { it.amount() }
        .reduce(Price.ZERO, Price::plus)
}