package com.youArt.API.controller

import com.youArt.API.DTO.post.ArtDto
import com.youArt.API.DTO.update.ArtUpdateDto
import com.youArt.API.DTO.view.ArtView
import com.youArt.API.DTO.view.CommentListView
import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import com.youArt.API.service.impl.ArtService
import com.youArt.API.service.impl.CommentService
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
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/arts")
class ArtController(
    private val artService: ArtService,
    private val commentService: CommentService
) {
    @PostMapping
    fun create(@RequestBody artDto: ArtDto): ResponseEntity<String> {
        val responseArt = this.artService.create(artDto.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("the art with title: ${responseArt.title} was posted successfully")
    }

    @GetMapping("/{id}")
    fun readById(@PathVariable id: Long): ResponseEntity<ArtView> {
        val art: Art = this.artService.readById(id)

        return ResponseEntity.status(HttpStatus.OK).body(ArtView(art))
    }

    @GetMapping("/comments/{id}")
    fun readAllCommentsByArt(@PathVariable id: Long): ResponseEntity<List<CommentListView>> {
        val commentListView: List<CommentListView> = this.commentService.readAllByArt(id)
            .stream()
            .map { comment: Comment -> CommentListView(comment) }
            .collect(Collectors.toList())

        return ResponseEntity.status(HttpStatus.OK).body(commentListView)
    }

    @PatchMapping("/{id}")
    fun updateById (@PathVariable id: Long,
                    @RequestParam(value = "requestId") requestId: Long,
                    @RequestBody artUpdateDto: ArtUpdateDto):ResponseEntity<ArtView> {
        val art: Art = this.artService.readById(id)

        if(art.user?.id == requestId) {
            val artToUpdate: Art = artUpdateDto.toEntity(art)
            val artUpdated: Art = this.artService.create(artToUpdate)
            return ResponseEntity.status(HttpStatus.OK).body(ArtView(artUpdated))
        }else {
            throw RuntimeException("you are not the user that create this account to update it")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long,
                   @RequestParam requestId: Long) {
        this.artService.deleteById(id,requestId)
    }

}