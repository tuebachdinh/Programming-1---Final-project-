package o1.adventure

import scala.collection.mutable.{Buffer, Map}

/** The class `Room` represents locations in the text adventure game world. The 
  * player can enter each room he chooses and decide to elaborate the room or not.
  * Elaborating the room means receiving the hint or the behind story of the room.
  * A room can contain item, npc or neither of them.
  * @param name         the name of the room
  * @param description  a basic description of the room */
class Room(var name: String, var description: String):

  private var items =  Buffer[Item]()
  private var npcs = Buffer[NPC]()
  
  /** This will be added at the Prison_Escape */
  var informationOfNPC = Map[String, String]("Personality" -> "Unknown", "Relationship" -> "Unknown", "Past" -> "Unknown")
  
  /** Next round accessible rooms. For example, in round 1, accessible rooms of "prison" are
   * Room_1, Room_2, Room_3 */
  var accessibleRooms = Buffer[Room]()

  /** Behind story of each room, which would be appeared only and only when the player want to elaborate
    * the room. */
  var behindStory = ""

  def addNPC(npc: NPC): Unit =
    this.npcs += npc

  def addItem(item: Item): Unit =
    this.items += item

  def hasNPCs: Boolean =
    this.npcs.size != 0

  def hasItem: Boolean =
    this.items.size != 0

  def addInformationOfNPC(topic: String, content: String) = 
    this.informationOfNPC(topic) = content
  
  def addAccessibleRooms(rooms: Buffer[Room]) =
    this.accessibleRooms = this.accessibleRooms ++ rooms

  def addBehindStory(story: String) =
    this.behindStory = this.behindStory + story

  /** Return the full discription of the room based on whether it contains an item,
    * an npc, or neither of them. */

  def fullDescription =
    var result = ""
    if this.items.size != 0 then
      result = this.description + "\n" + this.items(0).description
    else if this.npcs.size != 0 then
      result = this.description + "\n" + this.npcs(0).description
    else 
      result = this.description
      
    result


  /** Returns a single-line description of the room for debugging purposes. */
  override def toString = this.name

end Room

