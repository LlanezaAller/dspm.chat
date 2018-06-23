package uo206367.dspm.miw.dsdmchatt.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class User {
    /**
     * public User(String userName, String password){
     * this.userName = userName;
     * this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
     * }
     */

    @PrimaryKey
    var id: Int = 0

    var userName: String? = null
    var password: String? = null
    var data: String? = null
}
