package com.example.myapplication

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.window.SplashScreen
import androidx.annotation.RequiresApi

import babacan.Game.MyPoint
import babacan.Game.MyRectangle
import com.babacan05.ewggame.Ad
import com.babacan05.ewggame.AdMobInterstitial
import com.babacan05.ewggame.MainActivity

import com.babacan05.ewggame.R




class MyGame : SurfaceView, SurfaceHolder.Callback, Runnable {
    /**
     * Holds the surface frame
     */




    private var holder: SurfaceHolder? = null
    private val mPaint:Paint= Paint()
    private val textPaint:Paint= Paint()
    private val textPaint2:Paint= Paint()
    //private val gestures=GestureDetectorCompat(context,GestureListener())
    private lateinit var canvas:Canvas
    lateinit var houseBitmap:Bitmap
    lateinit var electricBitmap: Bitmap
    lateinit var scaledHouse:Bitmap
    lateinit var scaledElectric:Bitmap
    lateinit var scaledWater:Bitmap
    lateinit var waterBitmap: Bitmap
    lateinit var gasBitmap: Bitmap
    lateinit var scaledGas:Bitmap
lateinit var rectButton:RectF

    /**
     * Draw thread
     */
    private var drawThread: Thread? = null

    /**
     * True when the surface is ready to draw
     */
    private var surfaceReady = false

    /**
     * Drawing thread flag
     */
    private var drawingActive = false
    private var context: Context? = null

    //Paint paint;




    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(context: Context?) : super(context) {
        init(context)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    @TargetApi(21)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun init(c: Context?) {
        context = c
        val holder = getHolder()
        holder.addCallback(this)
        isFocusable = true
        houseBitmap=BitmapFactory.decodeResource(resources, R.drawable.house)
        electricBitmap=BitmapFactory.decodeResource(resources,R.drawable.electric)
        waterBitmap=BitmapFactory.decodeResource(resources,R.drawable.water)
        scaledHouse=Bitmap.createScaledBitmap(houseBitmap,MyGame.x/10,MyGame.y/7,true)
        scaledElectric=Bitmap.createScaledBitmap(electricBitmap,MyGame.x/10,MyGame.y/7,true)
        scaledWater=Bitmap.createScaledBitmap(waterBitmap,MyGame.x/10,MyGame.y/7,true)
        gasBitmap=BitmapFactory.decodeResource(resources,R.drawable.gas)
        scaledGas=Bitmap.createScaledBitmap(gasBitmap,MyGame.x/10,MyGame.y/7,true)
rectButton= RectF(MyGame.x/10f,MyGame.y/7f,MyGame.x/10f,MyGame.y/7f)

        houseBitmap.recycle()
        electricBitmap.recycle()
        waterBitmap.recycle()
        gasBitmap.recycle()

        mPaint.setAntiAlias(true)
        mPaint.setDither(true)

        mPaint.setColor(Color.argb(255, 0, 0, 10))
        mPaint.setStyle(Paint.Style.STROKE)
        mPaint.setStrokeWidth(3f)


        textPaint.setColor(Color.argb(100, 10, 100, 10));
        textPaint.setTextSize(MyGame.x/30f)
        textPaint.setAntiAlias(true)
textPaint2.setColor(Color.argb(100, 80, 100, 10));
textPaint2.setTextSize(MyGame.x/40f)
textPaint2.setAntiAlias(true)






    }



    fun render(canvas: Canvas) {
        canvas.drawRGB(255,255,255)

        drawPaths(canvas)
        drawHouses(canvas)
        drawSources(canvas)


        drawRestartGame(canvas)
        drawCountDown(canvas)

    }



    private fun drawRestartGame(canvas: Canvas) {
canvas.drawText("RESTART",MyGame.x/30f,MyGame.y/20f*19
    ,textPaint2)
    }

    fun tick() {

        if(!Game.gameOver&&!Game.adsOn) {




            Game.countDown.updateTime()



        }
        if(Game.gameOver&&!Game.adsOn){
            Game.adsOn=true
            Game.gameOver=false
            Game.myPathList.removeAll { true }

            Game.houses.forEach{house->house.cleanAllSources()}
            val intent = Intent(getContext(), MainActivity::class.java) // Ana aktiviteye geçiş yapılacak aktiviteyi belirtin
            context?.startActivity(intent)








        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (width == 0 || height == 0) {
            return
        }
        // resize your UI
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        this.holder = holder
        if (drawThread != null) {
            Log.d(LOGTAG, "draw thread still active..")
            drawingActive = false
            try {
                drawThread!!.join()
            } catch (e: InterruptedException) {
            }
        }
        surfaceReady = true
        startDrawThread()
        Log.d(LOGTAG, "Created")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
// Surface is not used anymore - stop the drawing thread
        stopDrawThread()
        // and release the surface
        holder.surface.release()
        this.holder = null
        surfaceReady = false
        Log.d(LOGTAG, "Destroyed")
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {

                if (Game.creathingPath == null) Game.handleSourceSelecting(
                    MyPoint(
                        event.x,
                        event.y
                    )
                )
            //oyun restart yapma geçici olarak buraya eklencek
                if(MyRectangle(MyPoint(MyGame.x/30f,MyGame.y/20f*17),MyGame.x/5f,MyGame.y/3f) .isPointInRectangle(MyPoint(event.x,event.y))){
                    Game.countDown.second=0.02

                }

            }

            MotionEvent.ACTION_MOVE -> {
                Game.creathingPath?.let {
                    Game.handleSourceMoving(event.x, event.y)
                    if(Game.adsOn)Game.adsOn=false

                }

            }

            MotionEvent.ACTION_UP -> {

                Game.creathingPath?.let {

                    Game.handleHouseSelecting(MyPoint(event.x, event.y))
                }


            }



        }
        return true
    }

    /**
     * Stops the drawing thread
     */
    fun stopDrawThread() {
        if (drawThread == null) {
            Log.d(LOGTAG, "DrawThread is null")
            return
        }
        drawingActive = false
        while (true) {
            try {
                Log.d(LOGTAG, "Request last frame")
                drawThread!!.join(5000)
                break
            } catch (e: Exception) {
                Log.e(LOGTAG, "Could not join with draw thread")
            }
        }
        drawThread = null
    }

    /**
     * Creates a new draw thread and starts it.
     */
    fun startDrawThread() {
        if (surfaceReady && drawThread == null) {
            drawThread = Thread(this, "Draw thread")
            drawingActive = true
            drawThread!!.start()
        }
    }

    override fun run() {
        Log.d(LOGTAG, "Draw thread started")
        var frameStartTime: Long
        var frameTime: Long
        if (Build.BRAND.equals("google", ignoreCase = true) &&
            Build.MANUFACTURER.equals("asus", ignoreCase = true) &&
            Build.MODEL.equals("Nexus 7", ignoreCase = true)
        ) {
            Log.w(LOGTAG, "Sleep 500ms (Device: Asus Nexus 7)")
            try {
                Thread.sleep(500)
            } catch (ignored: InterruptedException) {
            }
        }
        while (drawingActive) {
            if (getHolder() == null) {
                return
            }
            frameStartTime = System.nanoTime()
            val canvas = getHolder().lockCanvas()
            if (canvas != null) {
                try {
                    synchronized(getHolder()) {

                        render(canvas)}
                        tick()
                    
                } finally {
                    getHolder().unlockCanvasAndPost(canvas)
                }
            }
            // calculate the time required to draw the frame in ms
            frameTime = (System.nanoTime() - frameStartTime) / 1000000
            if (frameTime < MAX_FRAME_TIME) {
                try {
                    Thread.sleep(MAX_FRAME_TIME - frameTime)
                } catch (e: InterruptedException) {
// ignore
                }
            }
        }
        Log.d(LOGTAG, "Draw thread finished")
    }

    companion object {
        /**
         * Time per frame for 60 FPS
         */

        var y= 0
        var x=0


        private const val MAX_FRAME_TIME = (1000.0 / 20.0).toInt()
        private const val LOGTAG = "surface"
    }

    /*private inline fun drawPaths(canvas: Canvas) {
        Game.myPathList.forEach{path->path.lines.forEach{line->canvas.drawLine(line.p1.x,line.p1.y,line.p2.x,line.p2.y,mPaint)}}
       Game.creathingPath?.let{it.lines.forEach{line->canvas.drawLine(line.p1.x,line.p1.y,line.p2.x,line.p2.y,mPaint)}}
    }*/
    private inline fun drawPaths(canvas: Canvas) {

        var list= Game.myPathList.toList()

            .forEach{path->path.lines.forEach{line->canvas.drawLine(line.p1.x,line.p1.y,line.p2.x,line.p2.y,mPaint)}}



        Game.creathingPath?.let{val lines=it.lines.toList().forEach{line->canvas.drawLine(line.p1.x,line.p1.y,line.p2.x,line.p2.y,mPaint)}}
    }
    var x=1
    private inline fun drawSources(canvas: Canvas) {
        Game.sources.forEach{
            if(x==2){
                canvas.drawBitmap(scaledWater,it.shape.p1.x,it.shape.p1.y,null)

            }else if(x==3){
                canvas.drawBitmap(scaledGas,it.shape.p1.x,it.shape.p1.y,null)}
            else{

                canvas.drawBitmap(scaledElectric,it.shape.p1.x,it.shape.p1.y,null)}
            x=x+1

        }
        x=1
    }

    private inline fun drawHouses(canvas: Canvas) {
        Game.houses.forEach{canvas.drawBitmap(scaledHouse,it.rectangle.p1.x,it.rectangle.p1.y,null)}
    }


    private fun drawCountDown(canvas: Canvas) {
        Math.round(Game.countDown.second).toString()
        canvas.drawText(Math.round(Game.countDown.second).toString(),MyGame.x/30f,MyGame.y/20f
            ,textPaint)
    }


}