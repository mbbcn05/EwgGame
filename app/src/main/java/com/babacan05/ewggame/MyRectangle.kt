package babacan.Game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.babacan05.ewggame.MainActivity

class MyRectangle (val p1:MyPoint, val width: Float = MainActivity.x/10f, val height: Float =MainActivity.y/7f){
    val rect= Rect(Offset(p1.x,p1.y), Size(width,height))

    val points=listOf<MyPoint>(p1,MyPoint(p1.x+width,p1.y),MyPoint(p1.x+width,p1.y+height),
        MyPoint(p1.x,p1.y+height))


    fun distanceTo(point: MyPoint):Double{
        return points.map(point::distance).reduce{d1,d2->d1+d2}

    }

    //fun contains(point: MyPoint):Boolean= distanceTo(point).toFloat() ==width+height
    fun isPointInRectangle(point:MyPoint):Boolean{
        val result=(point.x> p1.x &&point.x<p1.x+width&&point.y>p1.y&&point.y<p1.y+height)

        return (result)
    }
}