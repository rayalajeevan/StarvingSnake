package com.example.starvingsnake

public class SnakeDots {
    private  var positionX:Int = 0
    private  var positionY:Int = 0

    constructor(positionX:Int,positionY:Int){
        this.positionX=positionX
        this.positionY=positionY
    }

    fun setPositionX(position:Int){
        this.positionX=position
    }
    fun setPositionY(position:Int){
        this.positionY=position
    }
    fun getPositionX():Int{
        return this.positionX
    }
    fun getPositionY():Int{
        return this.positionY
    }


}
