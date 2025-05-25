package com.task.model

class Tree(val rootId: Long) {

    val children: MutableList<Tree> = mutableListOf()

}