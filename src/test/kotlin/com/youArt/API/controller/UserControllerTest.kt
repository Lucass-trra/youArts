package com.youArt.API.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.youArt.API.DTO.post.UserDto
import com.youArt.API.DTO.update.UserUpdateDto
import com.youArt.API.entity.User
import com.youArt.API.repository.UserRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.Random

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/users"
    }
    @BeforeEach fun setup() = userRepository.deleteAll()

    @AfterAll fun  tearDown() = userRepository.deleteAll()

    //Post requests
    @Test
    fun `should to create a user and return 201 status code`() {
        //create a userDto and convert it for string
        val userDto: UserDto = buildUserDto()
        val dtoAsString: String = objectMapper.writeValueAsString(userDto)

        //making a post request and send the data through the ObjectMapper
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON).content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lucas Terra"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("71165381"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("lucas@gmail.com"))
            .andDo { MockMvcResultHandlers.print() }
    }

    @Test
    fun `should not create a user because some data are equals`() {
        userRepository.save(buildUserDto().toEntity())
        val userDto: UserDto = buildUserDto()
        val dtoAsString: String = objectMapper.writeValueAsString(userDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON).content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andDo { MockMvcResultHandlers.print() }
    }

    @Test
    fun `should not create a user because some field are empty`()  {
        val userDto: UserDto = buildUserDto(name = "")
        val dtoAsString: String = objectMapper.writeValueAsString(userDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON).content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad request! please consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
    }

    //Get requests
    @Test
    fun `should find a user by id`() {
        val user: User = userRepository.save(buildUserDto().toEntity())

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${user.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lucas Terra"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("71165381"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("lucas@gmail.com"))
            .andDo { MockMvcResultHandlers.print() }
    }


    @Test
    fun `should not find a user by id because the user does not exist`() {

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${7}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad request! please consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.youArt.API.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo { MockMvcResultHandlers.print() }
    }


    //Delete requests
    @Test
    fun `should delete a user by id based on the requestId`() {
        val user: User = userRepository.save(buildUserDto().toEntity())

        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${user.id}?requestId=${user.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete a user by id because the user does not exist`() {
        val invalidId: Long = Random().nextLong()

        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/$invalidId?requestId=${5}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad request! please consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.youArt.API.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete a user by id, because the requestId is not equals id`() {
        val user: User = userRepository.save(buildUserDto().toEntity())
        val invalidRequestId: Long = Random().nextLong()

        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${user.id}?requestId=${invalidRequestId}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad request! please consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.youArt.API.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }


    //Update requests
    @Test
    fun `should update a user by id, based on requestId`() {
        val user: User = userRepository.save(buildUserDto().toEntity())
        val userUpdateDto: UserUpdateDto = buildUserUpdateDto()
        val dtoAsString: String = objectMapper.writeValueAsString(userUpdateDto)

        mockMvc.perform(MockMvcRequestBuilders.patch("$URL/${user.id}?requestId=${user.id}")
            .contentType(MediaType.APPLICATION_JSON).content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lucas Rafael"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("8273892"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("lucas@gmail.com"))
            .andDo { MockMvcResultHandlers.print() }
    }

    @Test
    fun `should not update a user by id based on requestId because the user does not exist`() {
        val invalidId: Long = Random().nextLong()
        val userUpdateDto: UserUpdateDto = buildUserUpdateDto()
        val dtoAsString: String = objectMapper.writeValueAsString(userUpdateDto)

        mockMvc.perform(MockMvcRequestBuilders.patch("$URL/${invalidId}?requestId=${invalidId}")
            .contentType(MediaType.APPLICATION_JSON).content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad request! please consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.youArt.API.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty).andDo { MockMvcResultHandlers.print() }
    }

    @Test
    fun `should not update a user by id, because the requestId is not equals id`() {
        val user: User = userRepository.save(buildUserDto().toEntity())
        val invalidRequestId: Long = Random().nextLong()
        val userUpdateDto: UserUpdateDto = buildUserUpdateDto()
        val dtoAsString: String = objectMapper.writeValueAsString(userUpdateDto)

        mockMvc.perform(MockMvcRequestBuilders.patch("$URL/${user.id}?requestId=${invalidRequestId}")
            .contentType(MediaType.APPLICATION_JSON).content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad request! please consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.youArt.API.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty).andDo { MockMvcResultHandlers.print() }

    }





    private fun buildUserDto(
        name: String = "Lucas Terra",
        phoneNumber: Long = 71165381,
        emailAddress: String = "lucas@gmail.com",
        password: String = "82gs72gd"

    ) = UserDto(
        name = name,
        phoneNumber = phoneNumber,
        emailAddress = emailAddress,
        password = password
    )


    private fun buildUserUpdateDto(
        name: String = "Lucas Rafael",
        phoneNumber: Long = 8273892,
        password: String = "9813hgw66t62"

    ) = UserUpdateDto(
        name = name,
        phoneNumber = phoneNumber,
        password = password
    )

}

