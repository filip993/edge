package com.task.controller

import com.task.http.ApiResponse
import com.task.model.Edge
import com.task.model.Tree
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.task.service.EdgeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/api/edge")
class EdgeController(val edgeService: EdgeService) {

    @PostMapping
    fun createEdge(@RequestBody edge: Edge): ResponseEntity<ApiResponse<Int>> {
        val result = edgeService.createEdge(edge.fromId, edge.toId);
        return ResponseEntity.ok(ApiResponse(success = true, data = result))

    }
    @GetMapping(path = ["/{id}/tree"])
    fun getTree(@PathVariable id: Long): ResponseEntity<ApiResponse<Tree>> {
        return ResponseEntity.ok(ApiResponse(success = true, data = this.edgeService.getTree(id)))
    }

    @DeleteMapping
    fun deleteEdge(@RequestBody edge: Edge): ResponseEntity<ApiResponse<Int>> {
        val result = edgeService.deleteEdge(edge.fromId, edge.toId);
        return ResponseEntity.ok(ApiResponse(success = true, data = result))
    }
}