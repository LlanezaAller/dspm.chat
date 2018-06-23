package uo206367.dspm.miw.dsdmchatt.model

class Data {

    var operation: String? = null
    var name: String? = null
    var userName: String? = null
    var text: String? = null

    constructor(operation: String, name: String, userName: String, text: String) {
        this.operation = operation
        this.name = name
        this.userName = userName
        this.text = text
    }

    constructor(name: String, text: String) {
        this.operation = "text"
        this.name = name
        this.userName = name
        this.text = text
    }
}
