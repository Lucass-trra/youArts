package com.youArt.API.service.impl

import com.youArt.API.entity.Comment
import com.youArt.API.repository.CommentRepository
import com.youArt.API.service.IGeneralServices
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userService: UserService
): IGeneralServices<Comment> {
    override fun create(obj: Comment): Comment {
        obj.apply {
            user = userService.readById(obj.user?.id!!)
        }

        return this.commentRepository.save(obj)
    }
    override fun readById(id: Long): Comment = this.commentRepository.findById(id).orElseThrow {
            RuntimeException("Comment with the id $id was not found")
    }
    override fun deleteById(id: Long, requestId: Long) {
        val comment: Comment = this.commentRepository.findById(id).orElseThrow {
            RuntimeException("this Comment does not exist")
        }

        if(comment.user?.id == requestId || comment.art?.user?.id == requestId)  {
            this.commentRepository.delete(comment)
        }else {
            throw RuntimeException("you are not the creator of this comment or the art to delete it")
        }
    }

}