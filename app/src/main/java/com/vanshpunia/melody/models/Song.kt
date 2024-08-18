package com.vanshpunia.melody.models

class Song {
    var id : String? = null
    var title : String? = null
    var singer : String? = null
    var url : String? = null
    var coverUrl : String? = null
    var liked : Boolean? = false

    constructor()

    constructor(id: String?, title: String?, singer: String?, url: String?, coverUrl: String?, liked: Boolean?) {
        this.id = id
        this.title = title
        this.singer = singer
        this.url = url
        this.coverUrl = coverUrl
        this.liked = liked
    }


}