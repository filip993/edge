package com.task.exceptions

class TreeNotFoundException(message: String = "Tree does not exist"): RuntimeException(message)  {
}