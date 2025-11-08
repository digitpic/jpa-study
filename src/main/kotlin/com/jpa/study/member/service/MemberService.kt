package com.jpa.study.domain.member.service

import com.jpa.study.domain.member.model.Member
import com.jpa.study.domain.member.repository.MemberRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val repository: MemberRepository
) {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    /* === [예제 1번] === */
    @Transactional
    fun includeTransactional(): Member {
        val member = repository.findById(1L).get()
        member.age = 35
        repository.save(member)
        println("result: ${member.age}")
        return member
    }

    /* === [예제 2번] === */
    fun excludeTransactional(): Member {
        val member = repository.findById(1L).get()
        member.age = 35
        repository.save(member)
        println("result: ${member.age}")
        return member
    }

    /* === [예제 3번] === */
    @Transactional
    fun jpa(): Member {
        val member1 = repository.findById(1L).get()
        val member2 = repository.findById(1L).get()
        println("result: ${member1 === member2}")
        return member1
    }

    /* === [예제 4번] === */
    @Transactional
    fun jpql(): Member {
        val member1 = repository.findById(1L).get()
        val member2 = repository.findByIdWithJPQL(1L)
        println("result: ${member1 === member2}")
        return member1
    }

    /* === [예제 5번] === */
    @Transactional
    fun nativeQuery(): Member {
        val member1 = repository.findById(1L).get()
        val member2 = repository.findByIdWithNative(1L)
        println("result: ${member1 === member2}")
        return member1
    }

    /* === [예제 6번] === */
    @Transactional
    fun flushInTheMiddle(): Member {
        val member = repository.findById(1L).get()
        member.age = 35
        entityManager.flush()
        println("result: ${member.age}")
        repository.save(member)
        return member
    }

    /*🤩 === [예제 7번] === 😇*/
    @Transactional
    fun complex(): Member {
        val alice = repository.findById(1L).get()
        val bob = repository.findById(2L).get()

        alice.age = 40
        repository.delete(bob)

        val newAlice = repository.findByIdWithJPQL(1L)
        val newBob = Member(id = 2L, name = "Bob", age = 99)
        entityManager.persist(newBob)
        entityManager.flush()

        val nativeBob = repository.findByIdWithNative(2L)
        repository.save(newAlice)
        repository.save(nativeBob)

        println("result(Alice): ${alice.age}")
        println("result(Bob): ${bob.age}")
        println("result(NewAlice): ${newAlice.age}")
        println("result(NewBob): ${newBob.age}")
        println("result(NativeBob): ${nativeBob.age}")

        return alice
    }
}
