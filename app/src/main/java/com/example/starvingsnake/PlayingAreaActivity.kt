package com.example.starvingsnake

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.concurrent.timerTask

class PlayingAreaActivity : AppCompatActivity(),SurfaceHolder.Callback {
    private lateinit  var surface_vew:SurfaceView

    private var snake_movable_position:String="r"

    private  lateinit var surface_holder:SurfaceHolder

    private  var  snake_dots = ArrayList<SnakeDots>()
    private  var game_score:Int=0

    private  var snake_max_length:Int=20
    private  var snake_default_length:Int=4
    private  var snake_color:Int=Color.BLACK
    private  var snake_moving_speed:Int=800

    private  var positionX:Int = 0
    private  var positionY:Int = 0

    private  lateinit var timer:Timer

    private var canvas:Canvas?=null
    private  var paint_color:Paint= Paint()
    private var score_view:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing_area)
        score_view=findViewById<TextView>(R.id.score_update_textView)
        surface_vew=findViewById(R.id.surface_view)

        var top_Btn:AppCompatImageButton=findViewById(R.id.top_btn)
        var down_Btn:AppCompatImageButton=findViewById(R.id.down_btn)
        var left_Btn:AppCompatImageButton=findViewById(R.id.left_btn)
        var right_Btn:AppCompatImageButton=findViewById(R.id.right_btn)

        surface_vew.holder.addCallback(this)

        top_Btn.setOnClickListener {
            if(snake_movable_position.equals("b")){
                snake_movable_position="t"
            }
        }

        down_Btn.setOnClickListener {
            if(snake_movable_position.equals("t")){
                snake_movable_position="b"
            }
        }

        left_Btn.setOnClickListener {
            if(snake_movable_position.equals("r")){
                snake_movable_position="l"
            }
        }

        right_Btn.setOnClickListener {
            if(snake_movable_position.equals("l")){
                snake_movable_position="r"
            }
        }


    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        this.surface_holder=surface_holder
        reset()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }


    fun reset(){
        snake_dots.clear()
        score_view?.setText("0")
        game_score=0
        snake_movable_position="r"

        var start_position:Int=(snake_max_length)*snake_default_length

        for(i in 0..start_position){
            var snake_dot:SnakeDots= SnakeDots(start_position,snake_max_length)
            snake_dots.add(snake_dot)
            start_position=start_position-(snake_max_length*2)

        }

        addPoint()

        moveSnake()
    }
    private fun addPoint(){
        var surface_width:Int=surface_vew.width-(snake_max_length*2)
        var surface_height:Int=surface_vew.height-(snake_max_length*2)

        var randonXposition:Int=Random().nextInt(surface_width/snake_max_length)
        var randonYposition:Int=Random().nextInt(surface_height/snake_max_length)

        if ((randonXposition % 2)!=0){
            randonXposition=randonXposition+1
        }
        if ((randonYposition % 2)!=0){
            randonYposition=randonYposition+1
        }
        positionX=(snake_max_length*randonXposition)+snake_max_length
        positionY=(snake_max_length*randonYposition)+snake_max_length
    }

    private  fun moveSnake(){
        Timer().scheduleAtFixedRate(object :TimerTask(){
            override fun run() {

                var headPosx=snake_dots.get(0).getPositionX()
                var headPosY=snake_dots.get(0).getPositionY()

                if (headPosx==positionX && headPosY==positionY){
                    growSnake()

                    addPoint()
                }

                if (snake_movable_position=="r"){
                    snake_dots.get(0).setPositionX(headPosx+(snake_max_length*2))
                    snake_dots.get(0).setPositionX(headPosY)
                }
                else if (snake_movable_position=="l"){
                    snake_dots.get(0).setPositionX(headPosx-(snake_max_length*2))
                    snake_dots.get(0).setPositionX(headPosY)
                }
                else if (snake_movable_position=="t"){
                    snake_dots.get(0).setPositionX(headPosx+(snake_max_length*2))
                    snake_dots.get(0).setPositionX(headPosY-(snake_max_length*2))
                }
                else if (snake_movable_position=="b"){
                    snake_dots.get(0).setPositionX(headPosx+(snake_max_length*2))
                    snake_dots.get(0).setPositionX(headPosY+(snake_max_length*2))
                }

                if (checkGameOver(headPosx,headPosY)){
                    timer.purge()
                    timer.cancel()
                    makeAlertDialog("Game Over","Your Score is "+game_score.toString())

                }
                else{
                    canvas=surface_holder.lockCanvas()
                    canvas?.drawColor(Color.BLACK,PorterDuff.Mode.CLEAR)
                    canvas?.drawCircle(snake_dots.get(0).getPositionX().toFloat(),
                        snake_dots.get(0).getPositionY().toFloat(), snake_max_length.toFloat(),createpaintColor()
                    )

                }
                canvas?.drawCircle(positionX.toFloat(),
                    positionY.toFloat(), snake_max_length.toFloat(),createpaintColor())
                for(i in 1..snake_dots.size){
                    var getTempPositionX=snake_dots.get(i).getPositionX()
                    var getTempPositionY=snake_dots.get(i).getPositionY()
                    snake_dots.get(i).setPositionX(headPosx)
                    snake_dots.get(i).setPositionY(headPosY)
                    canvas?.drawCircle(snake_dots.get(i).getPositionX().toFloat(),
                        snake_dots.get(i).getPositionY().toFloat(), snake_max_length.toFloat(),createpaintColor()
                    )
                }
                surface_holder.unlockCanvasAndPost(canvas)


            }
        },(1000-snake_moving_speed).toLong(),(1000-snake_moving_speed).toLong())
    }
    private  fun growSnake(){
        var snake_dot:SnakeDots= SnakeDots(0,0)
        snake_dots.add(snake_dot)
        game_score=game_score+1
        score_view?.setText(game_score.toString())

    }

    private  fun makeAlertDialog(title:String,message:String){
        val alert=AlertDialog.Builder(this)
        alert.setTitle(title)
        alert.setMessage(message)
        alert.setCancelable(false)
        alert.show()
    }

    private  fun checkGameOver(headPositionX:Int,headPositionY:Int):Boolean{
        var game_over:Boolean=false
        if (snake_dots.get(0).getPositionX()<0 || snake_dots.get(0).getPositionY()<0 || snake_dots.get(0).getPositionX()>=surface_vew.width || snake_dots.get(0).getPositionY()>=surface_vew.height){
            game_over=true
        }
        else{
            for (i in 0..snake_dots.size){
                if(headPositionX==snake_dots.get(i).getPositionX() && headPositionY==snake_dots.get(i).getPositionY()){
                    game_over=true
                    break
                }
            }
        }
        return  game_over
    }

    private  fun createpaintColor():Paint{
            paint_color.setColor(snake_color)
            paint_color.style=Paint.Style.FILL
            paint_color.isAntiAlias=true
            return  paint_color
    }
}






