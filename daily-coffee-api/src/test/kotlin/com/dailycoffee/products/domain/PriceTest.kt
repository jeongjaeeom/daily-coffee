package com.dailycoffee.products.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

@DisplayName("상품 가격 테스트")
internal class PriceTest {

    @ParameterizedTest
    @ValueSource(longs = [0, 16000L])
    fun `상품의 가격을 생성할 수 있다`(value: Long) {
        val actual = Price(BigDecimal.valueOf(value))
        assertThat(actual).isEqualTo(Price(value))
    }

    @Test
    fun `상품의 가격은 0원 이상이어야 한다`() {
        assertThatIllegalArgumentException()
            .isThrownBy { Price(-1) }
    }
}
