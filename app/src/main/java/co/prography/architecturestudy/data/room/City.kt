package co.prography.architecturestudy.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class City (@PrimaryKey var no: Int?, var url : String?, var city: String?, var favorite: Boolean? = false)