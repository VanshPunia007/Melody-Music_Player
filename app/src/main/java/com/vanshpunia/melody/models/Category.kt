package com.vanshpunia.melody.models

class Category {
    var name: String? = null
    var coverUrl: String? = null
    var songs : List<String>? = null
    constructor()
    constructor(name: String?, coverurl: String?, songs: List<String>?) {
        this.name = name
        this.coverUrl = coverurl
        this.songs = songs
    }
}