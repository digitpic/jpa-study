package com.jpa.study.member.service

import com.jpa.study.domain.member.service.MemberService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberServiceTest {

    @Autowired
    private lateinit var service: MemberService

    @Nested
    inner class Transactional_어노테이션_포함_여부 {
        @Test
        fun includeTransactional() {
            // when
            val member = service.includeTransactional()

            // then
            assertThat(member.age).isNotEqualTo(BEFORE_AGE)
        }

        @Test
        fun excludeTransactional() {
            // when
            val member = service.excludeTransactional()

            // then
            assertThat(member.age).isNotEqualTo(BEFORE_AGE)
        }
    }

    @Nested
    inner class JPA_JPQL_NativeQuery {
        @Test
        fun jpa_를_사용하는_경우() {
            // when
            val member = service.jpa()

            // then
            assertThat(member.age).isEqualTo(BEFORE_AGE)
        }

        @Test
        fun jpql_을_사용하는_경우() {
            // when
            val member = service.jpql()

            // then
            assertThat(member.age).isEqualTo(BEFORE_AGE)
        }

        @Test
        fun nativeQuery_를_사용하는_경우() {
            // when
            val member = service.nativeQuery()

            // then
            assertThat(member.age).isEqualTo(BEFORE_AGE)
        }
    }

    @Nested
    inner class 특정_상황 {
        @Test
        fun flushInTheMiddle() {
            // when
            val member = service.flushInTheMiddle()

            // then
            assertThat(member.age).isNotEqualTo(BEFORE_AGE)
        }

        @Test
        fun complex() {
            // when
            val member = service.complex()

            // then
            assertThat(member.age).isNotEqualTo(BEFORE_AGE)
        }
    }

    companion object {
        private const val BEFORE_AGE = 30
    }
}
