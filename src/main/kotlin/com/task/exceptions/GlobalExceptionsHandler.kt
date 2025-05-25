package com.task.exceptions

import com.task.http.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EdgeNotCreatedException::class)
    fun handleEdgeNotCreated(ex: EdgeNotCreatedException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse(success = false, error = ex.message))
    }

    @ExceptionHandler(EdgeNotDeletedException::class)
    fun handleEdgeNotDeleted(ex: EdgeNotDeletedException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse(success = false, error = ex.message))
    }
    @ExceptionHandler(TreeNotFoundException::class)
    fun handleTreeNotFound(ex: TreeNotFoundException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse(success = false, error = ex.message))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Unit>> {
        val message = "Malformed JSON request or wrong input type: ${ex.localizedMessage}"
        return ResponseEntity.badRequest().body(ApiResponse(success = false, error = message))
    }
    @ExceptionHandler(DatabaseException::class)
    fun handleDatabaseExceptioons(ex: DatabaseException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse(success = false, error = ex.message))
    }
}