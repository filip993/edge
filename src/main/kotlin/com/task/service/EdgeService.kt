package com.task.service

import com.task.exceptions.EdgeNotCreatedException
import com.task.exceptions.EdgeNotDeletedException
import com.task.model.Edge
import com.task.model.Tree
import org.springframework.stereotype.Service
import com.task.repository.EdgeRepository
import org.jooq.Cursor
import org.jooq.Record2

@Service
class EdgeService(private val repository: EdgeRepository) {

    fun createEdge(fromId: Long, toId: Long): Int {
       val createdEdge: Int =  this.repository.createEdge(fromId, toId);
        if(createdEdge > 0) {
            return createdEdge;
        }
        throw EdgeNotCreatedException()
    }

    fun getTree(id: Long): Tree {
        return this.buildTree(id);
    }

    fun deleteEdge(fromId: Long, toId: Long): Int {
        val deletedEdges: Int = this.repository.deleteIfExists(fromId, toId);
        if(deletedEdges > 0) {
            return deletedEdges;
        }
        throw EdgeNotDeletedException()
    }

    private fun buildTree(rootId: Long): Tree {
        val response: Cursor<Record2<Int, Int>> = this.repository.fetchTree(rootId);
        if(response.hasNext()) {
            val childrenMap: Map<Long, List<Long>> = response
                .asSequence()
                .map { Edge(it.value1().toLong(), it.value2().toLong()) }
                .groupBy({ it.fromId }, { it.toId })

            fun buildNode(nodeId: Long): Tree {
                val node = Tree(nodeId)
                val childIds = childrenMap[nodeId] ?: emptyList()
                node.children.addAll(childIds.map { buildNode(it) })
                return node
            }

            return buildNode(rootId)
        }
        else throw EdgeNotDeletedException();
    }
}