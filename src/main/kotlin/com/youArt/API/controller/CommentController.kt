package com.youArt.API.controller

import com.youArt.API.DTO.post.CommentDto
import com.youArt.API.DTO.update.CommentUpdateDto
import com.youArt.API.DTO.view.CommentView
import com.youArt.API.entity.Comment
import com.youArt.API.service.impl.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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

@RestController
@RequestMapping("api/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun create(@RequestBody @Valid commentDto: CommentDto): ResponseEntity<String> {
        val responseComment = this.commentService.create(commentDto.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("the comment with this username: ${responseComment.userName} was posted successfully")
    }

    @GetMapping("/{id}")
    fun readById(@PathVariable id: Long): ResponseEntity<CommentView> {
       val comment:Comment = this.commentService.readById(id)

        return ResponseEntity.status(HttpStatus.OK).body(CommentView(comment))
    }

    @PatchMapping("/{id}")
    fun updateById (@PathVariable id: Long,
                    @RequestParam(value = "requestId") requestId: Long,
                    @RequestBody @Valid commentUpdateDto:CommentUpdateDto):ResponseEntity<CommentView> {
        val comment: Comment = this.commentService.readById(id)

        if(comment.user?.id == requestId) {
            val commentToUpdate: Comment = commentUpdateDto.toEntity(comment)
            val commentUpdated: Comment = this.commentService.create(commentToUpdate)
            return ResponseEntity.status(HttpStatus.OK).body(CommentView(commentUpdated))
        }else {
            throw RuntimeException("you are not the user that create this account to update it")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long,
                   @RequestParam requestId: Long) {
        this.commentService.deleteById(id,requestId)
    }

}