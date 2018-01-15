package org.ignus.ignus18.domain.mappers

import org.ignus.ignus18.data.EventCategory
import org.ignus.ignus18.data.EventCategoryResult
import org.ignus.ignus18.domain.model.EventCategoryList
import java.sql.Timestamp
import java.text.DateFormat
import java.util.*
import org.ignus.ignus18.domain.model.EventCategory as ModelEventCategory

class EventCategoryMapper {
    fun convertFromDataModel(eventCategory: EventCategoryResult): EventCategoryList = EventCategoryList(eventCategory.list)

    private fun convertEventCategoryListToDomain(list: List<EventCategory>): List<ModelEventCategory> {
        return list.mapIndexed { _, eventCategory ->
            convertEventCategoryItemToDomain(eventCategory)
        }
    }

    private fun convertEventCategoryItemToDomain(eventCategory: EventCategory): ModelEventCategory {
        return ModelEventCategory(eventCategory.parent_type, eventCategory.name, eventCategory.cover, eventCategory.events)
    }

    private fun convertTimestamp(timestamp: Timestamp): String {
        val dtf = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dtf.format(timestamp)
    }
}