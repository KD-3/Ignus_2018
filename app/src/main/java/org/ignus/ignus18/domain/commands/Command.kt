package org.ignus.ignus18.domain.commands

interface Command<out T> {
    fun execute(): T
}

interface Command2<out T> {
    fun execute(x: String): T
}