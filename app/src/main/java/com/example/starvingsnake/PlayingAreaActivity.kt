package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.motion.widget.OnSwipe
import java.lang.Math.abs
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.concurrent.timerTask

class PlayingAreaActivity : AppCompatActivity(),SurfaceHolder.Callback,GestureDetector.OnGestureListener {
    private lateinit  var surface_vew:SurfaceView
    private var user_name:String="User"
    private var game_level:Int=1
    private  var level_speed:Int=150
    private  var game_pause:Boolean=false

    private var snake_movable_position:String="t"

    private  lateinit var surface_holder:SurfaceHolder

    private  var  snake_dots = ArrayList<SnakeDots>()
    private  var game_score:Int=0

    private  var snake_max_length:Int=28
    private  var snake_default_length:Int=3
    private var  snake_bonus_point_length:Int=40
    private  var snake_color:Int=Color.WHITE
    private  var snake_moving_speed:Int=2000

    private  var positionX:Int = 0
    private  var positionY:Int = 0

    private  var bonus_positionX:Int = 0
    private  var bonus_positionY:Int = 0
    private  var point_tounched_counter:Int = 0
    private var bonus_point_enabled:Boolean=false

    private  lateinit var timer:Timer

    private var canvas:Canvas?=null
    private  var paint_color:Paint= Paint()
    private var score_view:TextView?=null
    lateinit var gestureDetector: GestureDetector
    private val SWIPE_THRESHOLD: Int = 100
    private val SWIPE_VELOCITY_THRESHOLD: Int = 100
    private  lateinit var ani_slide_zoom:Animation
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing_area)
        score_view=findViewById<TextView>(R.id.score_update_textView)
        user_name=intent.getStringExtra("Username").toString()
        game_level=intent.getIntExtra("level",1).toInt()
        surface_vew=findViewById(R.id.surface_view)
        ani_slide_zoom=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.shake)
        surface_vew.holder.addCallback(this)
        gestureDetector= GestureDetector(this,this)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        var x=p0
        return  true
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
       return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
       return true
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        var diffX=p1?.x!!.minus(p0?.x!!)
        var diffY=p1?.y!!.minus(p0?.y!!)
        var a=""
        if (abs(diffX) > abs(diffY)) {
            if (abs(diffX) > SWIPE_THRESHOLD && abs(
                    p2
                ) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (diffX > 0) {
                    if(!snake_movable_position.equals("l")){
                        snake_movable_position="r"
                    }
                }
                else {
                    if(!snake_movable_position.equals("r")){
                        snake_movable_position="l"
                    }
                }
            }
        }
        else {
            if (abs(diffY) > SWIPE_THRESHOLD && abs(
                    p3
                ) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (diffY < 0) {
                    if(!snake_movable_position.equals("b")){
                        snake_movable_position="t"
                    }
                }
                else {
                    if(!snake_movable_position.equals("t")){
                        snake_movable_position="b"
                    }
                }
            }
        }
        return  true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        surface_holder=p0
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

        for(i in 0..snake_default_length-1){
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
    private fun addBonusPoint(){
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
        bonus_positionX=(snake_max_length*randonXposition)+snake_max_length
        bonus_positionY=(snake_max_length*randonYposition)+snake_max_length
    }
    fun moveToGameOver(){
        var db=DatabaseAdapter(this,null)
        db.addScore(user_name.toString(),game_score.toInt(),game_level.toString())
        val intent=Intent(this,GameOverActivity::class.java)
        intent.putExtra("Game_score",game_score)
        intent.putExtra("Username",user_name)
        intent.putExtra("level",game_level)
        startActivity(intent)
        finish()
    }

    private  fun moveSnake(){
        timer=Timer()
        if(game_level==1){
            level_speed=150
        }
        else if (game_level==2){
            level_speed=100
        }
        else if(game_level==3){
            level_speed=50
        }
        timer.scheduleAtFixedRate(object :TimerTask(){
            override fun run() {
                if (game_pause == false) {


                    var headPosx = snake_dots.get(0).getPositionX()
                    var headPosY = snake_dots.get(0).getPositionY()

                    if (headPosx == positionX && headPosY == positionY) {
                        point_tounched_counter = point_tounched_counter + 1
                        growSnake()

                        addPoint()
                    }

                    if (snake_movable_position == "r") {
                        snake_dots.get(0).setPositionX(headPosx + (snake_max_length * 2))
                        snake_dots.get(0).setPositionY(headPosY)
                    } else if (snake_movable_position == "l") {
                        snake_dots.get(0).setPositionX(headPosx - (snake_max_length * 2))
                        snake_dots.get(0).setPositionY(headPosY)
                    } else if (snake_movable_position == "t") {
                        snake_dots.get(0).setPositionX(headPosx)
                        snake_dots.get(0).setPositionY(headPosY - (snake_max_length * 2))
                    } else if (snake_movable_position == "b") {
                        snake_dots.get(0).setPositionX(headPosx)
                        snake_dots.get(0).setPositionY(headPosY + (snake_max_length * 2))
                    }

                    if (checkGameOver(headPosx, headPosY)) {
                        timer.purge()
                        timer.cancel()
                        this@PlayingAreaActivity.runOnUiThread(Runnable {
                            surface_vew.startAnimation(ani_slide_zoom)
                        })
                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            moveToGameOver()
                        }, 1000)


                    } else {
                        canvas = surface_holder.lockCanvas()
                        canvas?.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
                        canvas?.drawCircle(
                            snake_dots.get(0).getPositionX().toFloat(),
                            snake_dots.get(0).getPositionY().toFloat(),
                            snake_max_length.toFloat(),
                            createpaintColor()
                        )
                        if (bonus_point_enabled && headPosx == bonus_positionX && headPosY == bonus_positionY) {
                            bonus_point_enabled = false
                            bonus_positionX = 0
                            bonus_positionY = 0
                            point_tounched_counter = 0
                            game_score += 10
                            decreaseSnakeLength()
                            this@PlayingAreaActivity.runOnUiThread(Runnable {
                                score_view?.setText(game_score.toString())
                                score_view?.startAnimation(ani_slide_zoom)
                            })
                        }
                        canvas?.drawCircle(
                            positionX.toFloat(),
                            positionY.toFloat(), snake_max_length.toFloat(), createpaintColor()
                        )
                        for (i in 1..snake_dots.size - 1) {
                            var getTempPositionX = snake_dots.get(i).getPositionX()
                            var getTempPositionY = snake_dots.get(i).getPositionY()
                            snake_dots.get(i).setPositionX(headPosx)
                            snake_dots.get(i).setPositionY(headPosY)
                            canvas?.drawCircle(
                                snake_dots.get(i).getPositionX().toFloat(),
                                snake_dots.get(i).getPositionY().toFloat(),
                                snake_max_length.toFloat(),
                                createpaintColor()
                            )
                            headPosx = getTempPositionX
                            headPosY = getTempPositionY
                        }
                        if (point_tounched_counter >= 5) {
                            if (bonus_point_enabled == false) {
                                addBonusPoint()
                            }
                            bonus_point_enabled = true
                            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                if (bonus_point_enabled) {
                                    bonus_point_enabled = false
                                    point_tounched_counter = 0
                                }
                            }, 5000)
                            if (snake_bonus_point_length == 40) {
                                snake_bonus_point_length = 32
                            } else {
                                snake_bonus_point_length = 40
                            }
                            canvas?.drawCircle(
                                bonus_positionX.toFloat(),
                                bonus_positionY.toFloat(),
                                snake_bonus_point_length.toFloat(),
                                createpaintColor(true)
                            )
                        }

                        surface_holder.unlockCanvasAndPost(canvas)
                    }

                }
            }
        },level_speed.toLong(),level_speed.toLong())


    }



    private  fun growSnake(){
        var snake_dot:SnakeDots= SnakeDots(0,0)
        snake_dots.add(snake_dot)
        game_score=game_score+1
        this@PlayingAreaActivity.runOnUiThread(Runnable {
            score_view?.setText(game_score.toString())
        })


    }
    private  fun decreaseSnakeLength(){
        if(snake_dots.size>3) {
            for (i in 1..snake_dots.size - 3) {
                if(i>2){
                    break
                }
                snake_dots.removeLast()
            }
        }


    }



    private  fun checkGameOver(headPositionX:Int,headPositionY:Int):Boolean{
        var game_over:Boolean=false
        if (snake_dots.get(0).getPositionX()<0 || snake_dots.get(0).getPositionY()<0 || snake_dots.get(0).getPositionX()>=surface_vew.width || snake_dots.get(0).getPositionY()>=surface_vew.height){
            game_over=true
        }
        else{
            for (i in 1..snake_dots.size-1){
                if(headPositionX==snake_dots.get(i).getPositionX() && headPositionY==snake_dots.get(i).getPositionY()){
                    game_over=true
                    break
                }
            }
        }
        return  game_over
    }

    private  fun createpaintColor(bonus_point:Boolean=false):Paint{
            if(bonus_point){
                paint_color.setColor(Color.YELLOW)
            }

        else{
            paint_color.setColor(snake_color)
            }
            paint_color.style=Paint.Style.FILL
            paint_color.isAntiAlias=true
            return  paint_color
    }

    override fun onBackPressed() {
        game_pause=true
        makeAlertDialog()

    }

    fun makeAlertDialog(){
        var builder=AlertDialog.Builder(this)
        builder.setPositiveButton("Quit",DialogInterface.OnClickListener { dialogInterface, i ->
            timer.purge()
            timer.cancel()
            super.onBackPressed()
        })
        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            game_pause=false

        })
        val alert=builder.create()
        alert.setTitle("Are you sure")
        alert.show()
    }
}








