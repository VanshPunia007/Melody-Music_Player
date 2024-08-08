package com.vanshpunia.melody.models

class Category {
    var name: String? = null
    var coverurl: String? = null
    var songs : List<String>? = null
    constructor()
    constructor(name: String?, coverurl: String?, songs: List<String>?) {
        this.name = name
        this.coverurl = coverurl
        this.songs = songs
    }
}