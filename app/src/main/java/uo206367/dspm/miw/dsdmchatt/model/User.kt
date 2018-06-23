package uo206367.dspm.miw.dsdmchatt.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class User {

    @PrimaryKey
    var id: Int = 0

    var userName: String = ""
    var password: String = ""
    var data: String? = null
}
