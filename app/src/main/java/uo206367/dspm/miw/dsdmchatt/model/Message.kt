package uo206367.dspm.miw.dsdmchatt.model

import uo206367.dspm.miw.dsdmchatt.R

class Message {

    var section: String? = null
    var data: Data? = null

    constructor(section: String, data: Data) {
        this.section = section
        this.data = data
    }

    constructor(userName: String, text: String) {
        section = "messages"
        data = Data(userName, text)
    }

    fun printMessage(msg: String?): String {
        when (section) {
            "messages" -> return data!!.userName + ":\n\t\t" + data!!.text

            "people" -> return java.lang.String.format(msg, data!!.userName)

            else -> return ""
        }
    }
}
