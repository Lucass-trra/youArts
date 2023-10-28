package com.youArt.API.controller

import com.youArt.API.DTO.post.UserDto
import com.youArt.API.DTO.update.UserUpdateDto
import com.youArt.API.DTO.view.ArtListView
import com.youArt.API.DTO.view.CommentListView
import com.youArt.API.DTO.view.UserView
import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import com.youArt.API.entity.User
import com.youArt.API.service.impl.ArtService
import com.youArt.API.service.impl.CommentService
import com.youArt.API.service.impl.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("api/users")
class UserController(
    private val userService: UserService,
    private val commentService: CommentService,
    private val artService: ArtService
) {
    @PostMapping
    fun create(@RequestBody @Valid userDto: UserDto): ResponseEntity<String>  {
        val responseUser = this.userService.create(userDto.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("user ${responseUser.name} created successfully")
    }

    @GetMapping("/{id}")
    fun readById(@PathVariable id: Long): ResponseEntity<UserView> {
        val responseUser: User = this.userService.readById(id)
        return ResponseEntity.status(HttpStatus.OK).body(UserView(responseUser))
    }

    @GetMapping("/arts/{id}")
    fun readAllArtsByUser(@PathVariable id: Long): ResponseEntity<List<ArtListView>> {
        val artViewList: List<ArtListView> = this.artService.readAllByUser(id)
            .stream()
            .map { art: Art -> ArtListView(art) }
            .collect(Collectors.toList())

        return ResponseEntity.status(HttpStatus.OK).body(artViewList)
    }

    @GetMapping("/comments/{id}")
    fun readAllCommentsByUser(@PathVariable id: Long): ResponseEntity<List<CommentListView>> {
        val commentListView: List<CommentListView> = this.commentService.readAllByUser(id)
            .stream()
            .map { comment:Comment -> CommentListView(comment) }
            .collect(Collectors.toList())

        return ResponseEntity.status(HttpStatus.OK).body(commentListView)
    }

    @PatchMapping("/{id}")
    fun updateById(@PathVariable id: Long,
                   @RequestParam(value = "requestId") requestId: Long,
                   @RequestBody @Valid userUpdateDto: UserUpdateDto):ResponseEntity<UserView> {
        val user: User = this.userService.readById(id)

        if(user.id == requestId) {
            val userToUpdate: User =  userUpdateDto.toEntity(user)
            val userUpdated: User = this.userService.create(userToUpdate)
            return ResponseEntity.status(HttpStatus.OK).body(UserView(userUpdated))
        } else {
            throw RuntimeException("you are not the user that create this account to update it")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long,
                   @RequestParam(value = "requestId") requestId: Long)
    = this.userService.deleteById(id,requestId)

}