package com.dailycoffee.menus.application

import com.dailycoffee.menu
import com.dailycoffee.menuProduct
import com.dailycoffee.menuProductRequest
import com.dailycoffee.menuRequest
import com.dailycoffee.menus.domain.MenuRepository
import com.dailycoffee.menus.infra.InMemoryMenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal
import java.util.UUID

@DisplayName("메뉴 서비스 테스트")
class MenuServiceTest {
    private lateinit var menuService: MenuService
    private lateinit var menuRepository: MenuRepository

    @BeforeEach
    fun setUp() {
        menuRepository = InMemoryMenuRepository()
        menuService = MenuService(menuRepository)
    }

    @AfterEach
    internal fun tearDown() {
        menuRepository.deleteAll()
    }

    @Nested
    @DisplayName("메뉴 등록")
    inner class MenuCreate {

        @Test
        fun `1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다`() {
            val menuGroupId = UUID.randomUUID()
            val createMenuRequest = menuRequest(
                "아이스 카페 아메리카노",
                BigDecimal.valueOf(4_500L),
                true,
                menuGroupId,
                listOf(menuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(4_500L), 1L))
            )
            val actual = menuService.create(createMenuRequest)
            assertThat(actual.id).isNotNull
            assertThat(actual.name).isEqualTo("아이스 카페 아메리카노")
            assertThat(actual.price).isEqualTo(BigDecimal.valueOf(4_500L))
            assertThat(actual.displayed).isTrue
            assertThat(actual.menuGroupId).isEqualTo(menuGroupId)
            assertThat(actual.menuProducts).hasSize(1)
        }
    }

    @Nested
    @DisplayName("메뉴 가격 변경")
    inner class MenuChangePrice {
        @Test
        fun `메뉴의 가격을 변경할 수 있다`() {
            val menu = menuRepository.save(
                menu(
                    "아이스 카페 아메리카노",
                    BigDecimal.valueOf(4_500L),
                    true,
                    UUID.randomUUID(),
                    listOf(menuProduct(UUID.randomUUID(), BigDecimal.valueOf(4_500L), 1))
                )
            )
            assertDoesNotThrow {
                menuService.changePrice(menu.id, ChangePriceRequest(BigDecimal.valueOf(4_100L)))
            }
        }
    }

    @Nested
    @DisplayName("메뉴 조회")
    inner class MenuRetrieve {
        @Test
        fun `메뉴의 목록을 조회할 수 있다`() {
            menuRepository.save(
                menu(
                    "아이스 카페 아메리카노",
                    BigDecimal.valueOf(4_500L),
                    true,
                    UUID.randomUUID(),
                    listOf(menuProduct(UUID.randomUUID(), BigDecimal.valueOf(4_500L), 1))
                )
            )
            menuRepository.save(
                menu(
                    "아이스 카페라떼",
                    BigDecimal.valueOf(5_500L),
                    true,
                    UUID.randomUUID(),
                    listOf(menuProduct(UUID.randomUUID(), BigDecimal.valueOf(5_500L), 1))
                )
            )

            val actual = menuService.findAll()
            assertThat(actual).hasSize(2)
            assertThat(actual).map(MenuResponse::name, MenuResponse::price)
                .contains(
                    Tuple.tuple("아이스 카페 아메리카노", BigDecimal.valueOf(4_500L)),
                    Tuple.tuple("아이스 카페라떼", BigDecimal.valueOf(5_500L)),
                )
        }
    }
}
