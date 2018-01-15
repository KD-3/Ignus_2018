package org.ignus.ignus18.domain.commands

interface Command<out T> {
    fun execute(): T
}