package org.ignus.ignus18.domain.commands

import org.ignus.ignus18.data.EventCategoryRequest
import org.ignus.ignus18.data.EventDetail
import org.ignus.ignus18.data.EventDetailRequest
import org.ignus.ignus18.domain.mappers.EventCategoryMapper
import org.ignus.ignus18.domain.model.EventCategoryList


class RequestEventCategoryCommand : Command<EventCategoryList> {
    override fun execute(): EventCategoryList {
        val eventCategoryRequest = EventCategoryRequest()
        return EventCategoryMapper().convertFromDataModel(eventCategoryRequest.execute())
    }
}

class RequestEventDetailCommand: Command2<EventDetail> {
    override fun execute(x:String): EventDetail {
        return EventDetailRequest().execute(x)
    }
}