package com.dailycoffee

import com.dailycoffee.menus.application.CreateMenuRequest
import com.dailycoffee.menus.application.MenuProductRequest
import com.dailycoffee.menus.domain.Menu
import com.dailycoffee.menus.domain.MenuGroup
import com.dailycoffee.menus.domain.MenuProduct
import java.math.BigDecimal
import java.util.UUID

fun menuGroup(name: String) = MenuGroup(name)

fun menu(
    name: String,
    price: BigDecimal,
    displayed: Boolean,
    menuGroupId: UUID,
    menuProducts: List<MenuProduct>
): Menu {
    return Menu(
        name = name,
        price = price,
        displayed = displayed,
        menuGroupId = menuGroupId,
        menuProducts = menuProducts,
    )
}

fun menuProduct(productId: UUID, price: BigDecimal, quantity: Long): MenuProduct {
    return MenuProduct(
        productId = productId,
        price = price,
        quantity = quantity
    )
}

fun menuRequest(
    name: String,
    price: BigDecimal,
    displayed: Boolean,
    menuGroupId: UUID,
    menuProducts: List<MenuProductRequest>
) = CreateMenuRequest(
    name,
    price,
    displayed,
    menuGroupId,
    menuProducts
)

fun menuProductRequest(productId: UUID, price: BigDecimal, quantity: Long) = MenuProductRequest(
    productId = productId,
    price = price,
    quantity = quantity,
)
