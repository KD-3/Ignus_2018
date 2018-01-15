package org.ignus.ignus18.data



data class Location(val name: String, val longitude: Double, val latitude: Double)

data class Organiser(val name: String, val phone: String, val avatar_url: String)
data class Event(val name: String, val cover_url: String, val timestamp: String, val unique_id: String, val location: Location, val min_team_size: Int, val max_team_size: Int, val organiser_list: List<Organiser>)
data class EventCategory(val parent_type: String, val name: String, val cover: String, val events: List<Event>)
data class EventCategoryResult(val list: List<EventCategory>)

//data class UserDetails(var name: String = "Ignus", var email:String = "ignus.org", var avatar_url: String = "")