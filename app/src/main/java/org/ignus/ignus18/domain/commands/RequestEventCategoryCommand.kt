package org.ignus.ignus18.domain.commands

import org.ignus.ignus18.data.EventCategoryRequest
import org.ignus.ignus18.domain.mappers.EventCategoryMapper
import org.ignus.ignus18.domain.model.EventCategoryList


class RequestEventCategoryCommand : Command<EventCategoryList> {
    override fun execute(): EventCategoryList {
        val eventCategoryRequest = EventCategoryRequest()
        return EventCategoryMapper().convertFromDataModel(eventCategoryRequest.execute())
    }
}