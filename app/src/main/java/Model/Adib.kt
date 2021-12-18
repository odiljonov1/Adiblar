package Model

import java.io.Serializable

class Adib  : Serializable {
    var imageUri: String? = null
    var nameAndLastname: String? = null
    var tugilganYili: String? = null
    var olganYili: String? = null
    var turi: Int? = null
    var info: String? = null

    constructor(
        imageUri: String?,
        nameAndLastname: String?,
        tugilganYili: String?,
        olganYili: String?,
        turi: Int?,
        info: String?
    ) {
        this.imageUri = imageUri
        this.nameAndLastname = nameAndLastname
        this.tugilganYili = tugilganYili
        this.olganYili = olganYili
        this.turi = turi
        this.info = info
    }

    constructor()

    override fun toString(): String {
        return "Adib(imageUri=$imageUri, nameAndLastname=$nameAndLastname, tugilganYili=$tugilganYili, olganYili=$olganYili, turi=$turi, info=$info)"
    }

}