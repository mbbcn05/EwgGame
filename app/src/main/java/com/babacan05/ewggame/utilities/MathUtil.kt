package babacan.Game

import androidx.compose.ui.geometry.Offset
import com.babacan05.ewggame.gamefiles.MyLine
import kotlin.math.max
import kotlin.math.min

fun Int.abs() =Math.abs(this)
fun doLinesIntersect(line1: MyLine, line2: MyLine): Boolean {

    val x1 = line1.p1.x
    val y1 = line1.p1.y
    val x2 = line1.p2.x

    val y2 = line1.p2.y

    val x3 = line2.p1.x
    val y3 = line2.p1.y
    val x4 = line2.p2.x
    val y4 = line2.p2.y


    if (x1 > x4 || x2 < x3 || y1 > y4 || y2 < y3) {

        return false
    }

    return true
}




fun doLinesIntersect(line1Start: Offset, line1End: Offset, line2Start: Offset, line2End: Offset): Boolean {
    val denominator = (line1End.y - line1Start.y) * (line2End.x - line2Start.x) - (line1End.x - line1Start.x) * (line2End.y - line2Start.y)

    if (denominator == 0f) {
        return false
    }

    val ua = ((line2End.x - line2Start.x) * (line1Start.y - line2Start.y) - (line2End.y - line2Start.y) * (line1Start.x - line2Start.x)) / denominator
    val ub = ((line1End.x - line1Start.x) * (line1Start.y - line2Start.y) - (line1End.y - line1Start.y) * (line1Start.x - line2Start.x)) / denominator

    val epsilon = 1e-5f

    return (ua >= -epsilon && ua <= 1f + epsilon && ub >= -epsilon && ub <= 1f + epsilon)
}




fun onSegment(p: Offset, q: Offset, r: Offset): Boolean {
    if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) &&
        q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y)
    ) return true
    return false
}

fun orientation(p: Offset, q: Offset, r: Offset): Int {
    val value = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
    if (value == 0.0f) return 0
    return if (value > 0) 1 else 2
}

fun doIntersect(p1: Offset, q1: Offset, p2: Offset, q2: Offset): Boolean {
    val o1 = orientation(p1, q1, p2)
    val o2 = orientation(p1, q1, q2)
    val o3 = orientation(p2, q2, p1)
    val o4 = orientation(p2, q2, q1)

    if (o1 != o2 && o3 != o4) return true

    if (o1 == 0 && onSegment(p1, p2, q1)) return true

    if (o2 == 0 && onSegment(p1, q2, q1)) return true

    if (o3 == 0 && onSegment(p2, p1, q2)) return true

    if (o4 == 0 && onSegment(p2, q1, q2)) return true

    return false
}
