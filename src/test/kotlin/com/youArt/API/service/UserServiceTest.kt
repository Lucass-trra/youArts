package com.youArt.API.service

import com.youArt.API.entity.User
import com.youArt.API.exception.BusinessException
import com.youArt.API.repository.UserRepository
import com.youArt.API.service.impl.UserService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.util.Optional
import java.util.Random


@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class UserServiceTest {
    @MockK lateinit var userRepository: UserRepository
    @InjectMockKs lateinit var userService: UserService

    @Test
    fun `should create a user`() {
        //create fake user
        val fakeUser: User = buildUser()

        // condition for every time that the userRepository is called, always a fakeUser to be returned
        every { userRepository.save(any()) } returns fakeUser

        //create a fakeUser in userService method(create)
        val actual: User = userService.create(fakeUser)

        //testing the return of userService method(create) with Assertions
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeUser)
        verify(exactly = 1) { userRepository.save(fakeUser) }
    }

    @Test
    fun `should find a user by id`() {
        //create a fakeId and a fakeUser
        val fakeId: Long = Random().nextLong()
        val fakeUser: User = buildUser(id = fakeId)

        //condition for every time that the userRepository is called, always a Optional fakeUser to be returned
        every { userRepository.findById(fakeId) } returns Optional.of(fakeUser)

        //find a fakeUser in userService method(readById)
        val actual: User = userService.readById(fakeId)

        //testing the return of userService method(readById) with Assertions
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeUser)
        Assertions.assertThat(actual).isExactlyInstanceOf(User::class.java)
        verify(exactly = 1) { userRepository.findById(fakeId) }
    }

    @Test
    fun `should not find a user by invalid id and throw a businessException`() {
        //create a fakeId
        val fakeId = Random().nextLong()

        //condition for every time that the userRepository.findById is called, always a empty Optional user returned
        every { userRepository.findById(fakeId) } returns Optional.empty()

        //testing Assertions for a user that was not found
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { userService.readById(fakeId) }
            .withMessage("the user with id $fakeId was not found")

        verify(exactly = 1) { userRepository.findById(fakeId) }
    }

    @Test
    fun `should delete a user by id if the requestId is equals id`() {
        //create a fakeUser and a fakeId
        val fakeId: Long = Random().nextLong()
        val fakeIdRequest: Long = fakeId
        val fakeUser:User = buildUser(id = fakeId)

        //condition for every time that a user was found, returned a optional of fakeUser
        every { userRepository.findById(fakeId) } returns Optional.of(fakeUser)

        //condition for every time that a user was deleted, just run the function
        every { userRepository.delete(fakeUser) } just runs

        //deleting a user based on id and the requestId
        userService.deleteById(fakeId,fakeIdRequest)

        //testing Assertions for user for delete it
        verify(exactly = 1) { userRepository.findById(fakeId) }
        verify(exactly = 1) { userRepository.delete(fakeUser) }
    }

    @Test
    fun `should not delete a user because the id is not equals requestId`() {
        //create a fakeUser and a fakeId
        val fakeId: Long = Random().nextLong()
        val fakeIdRequest: Long = Random().nextLong()
        val fakeUser:User = buildUser(id = fakeId)

        //condition for every time that a user was found, returned a optional of fakeUser
        every { userRepository.findById(fakeId) } returns Optional.of(fakeUser)

        //condition for every time that a user was deleted, just run the function
        every { userRepository.delete(fakeUser) } just runs

        //testing Assertions for business Exception for user for delete it
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { userService.deleteById(fakeId,fakeIdRequest) }
            .withMessage("you are not the user that create this account to delete it")
    }

    fun buildUser(
        name: String = "Lucas Terra",
        phoneNumber: Long = 71165381,
        emailAddress: String = "lucas@gmail.com",
        password: String = "82gs72gd",
        id: Long = 1L
    ) = User(
        name = name,
        phoneNumber = phoneNumber,
        emailAddress = emailAddress,
        password = password,
        id = id
    )
}