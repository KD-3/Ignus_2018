package org.ignus.ignus18.domain.model

import org.ignus.ignus18.data.Event
import org.ignus.ignus18.data.EventCategory

data class EventCategoryList(val eventCategories: List<EventCategory>)
data class EventCategory(val parent_type: String, val name: String, val cover: String, val events: List<Event>)