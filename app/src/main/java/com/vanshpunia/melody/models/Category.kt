package com.vanshpunia.melody.models

class Category {
    var name: String? = null
    var coverurl: String? = null

    constructor()
    constructor(name: String?, coverurl: String?) {
        this.name = name
        this.coverurl = coverurl
    }
}