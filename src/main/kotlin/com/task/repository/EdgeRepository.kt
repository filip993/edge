package com.task.repository

import com.example.jooq.tables.Edge
import com.task.exceptions.DatabaseException
import org.jooq.Cursor
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.jooq.impl.DSL.*
import org.jooq.impl.SQLDataType
import org.jooq.Record2
import org.jooq.exception.DataAccessException
import java.sql.SQLException

@Repository
class EdgeRepository(private val ctx: DSLContext) {
    val edge: Edge = Edge()

    fun createEdge(fromId: Long, toId: Long): Int {
        try {
        return ctx.insertInto(edge)
            .set(edge.FROM_ID, fromId)
            .set(edge.TO_ID, toId)
            .onConflict(edge.FROM_ID, edge.TO_ID)
            .doNothing()
            .execute()
        }
        catch (e: DataAccessException) {
            throw DatabaseException("Database error: ${e.message}")
        } catch (e: SQLException) {
            throw DatabaseException("SQL error: ${e.message}")
        } catch (e: Exception) {
            throw RuntimeException("Unexpected error: ${e.message}", e)
        }

    }

    fun deleteIfExists(fromId: Long, toId: Long): Int {
        try {
            return ctx.deleteFrom(edge)
                .where(edge.FROM_ID.eq(fromId))
                .and(edge.TO_ID.eq(toId))
                .execute()
        }
        catch (e: DataAccessException) {
            throw DatabaseException("Database error: ${e.message}")
        } catch (e: SQLException) {
            throw DatabaseException("SQL error: ${e.message}")
        } catch (e: Exception) {
            throw RuntimeException("Unexpected error: ${e.message}", e)
        }
    }

    fun fetchTree(rootId: Long): Cursor<Record2<Int, Int>> {

        try {
            val tree = name("tree").`as`(
                select(edge.FROM_ID, edge.TO_ID)
                    .from(edge)
                    .where(edge.FROM_ID.eq(rootId))
                    .unionAll(
                        select(edge.FROM_ID, edge.TO_ID)
                            .from(edge)
                            .join(table("tree"))
                            .on(edge.FROM_ID.eq(field("tree.to_id", SQLDataType.BIGINT)))
                    )
            )

            return ctx.withRecursive(tree)
                .select(field("from_id", Int::class.java), field("to_id", Int::class.java))
                .from(table("tree"))
                .fetchLazy()
        }
        catch (e: DataAccessException) {
            throw DatabaseException("Database error: ${e.message}")
        } catch (e: SQLException) {
            throw DatabaseException("SQL error: ${e.message}")
        } catch (e: Exception) {
            throw RuntimeException("Unexpected error: ${e.message}", e)
        }
    }
}