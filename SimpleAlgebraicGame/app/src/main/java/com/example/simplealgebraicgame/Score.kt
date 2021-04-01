package com.example.simplealgebraicgame

class Score {
    var id: Int = 0
    var username:String?=null
    var score:Int=0

    constructor(id:Int, username:String, score: Int){
        this.id=id
        this.username=username
        this.score=score
    }

    constructor(username: String, score: Int){
        this.username=username
        this.score=score
    }
}