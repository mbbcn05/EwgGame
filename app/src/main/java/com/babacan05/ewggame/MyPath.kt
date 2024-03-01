package babacan.Game

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import com.example.myapplication.Game
import com.example.myapplication.GameHouse


class MyPath (val source:GameSource,val game:Game) {
   // var lines = Collections.synchronizedList(mutableListOf<MyLine>())
    var lines= mutableListOf<MyLine>()
    var point: Offset? = null
    fun clipLinesInRectangle(rectangle: MyRectangle, rectangle2: MyRectangle): MyPath {

        val removingLines = mutableListOf<MyLine>()
        lines.forEach { line ->
            if (rectangle.rect.contains(line.p1) && rectangle.rect.contains(line.p2) ||
                rectangle2.rect.contains(line.p1) && rectangle2.rect.contains(line.p2)
            ) {
                removingLines.add(line)
            }
           // path.lines.addAll(lines.toList())
            //path.lines.removeAll((removingLines).toList())



        }
        val path = MyPath(source,game)
        val newLines=lines.toMutableList()
        newLines.removeAll(removingLines)
        path.lines = newLines
        return path
    }

    fun addLines(x: Float, y: Float, creathingLines: SnapshotStateList<MyLine>) {

        if (point != null) {
            val addinLine=MyLine(point!!, Offset(x, y))
            lines.add(addinLine)
creathingLines.add(addinLine)
        }
        point = Offset(x, y)
    }


   private fun intersectWithLine(line: MyLine): Boolean =
        lines.count { ln -> doIntersect(ln.p1, ln.p2,line.p1,line.p2) } > 0

  private  fun intersects(other: MyPath) = other.lines.count { line -> intersectWithLine(line) } > 0
fun intersectsWithPaths(paths:List<MyPath>):Boolean=paths.count{path->intersects(path)}>0




    fun isIntersectOtherHouses(gameHouse: GameHouse): Boolean {
        var intersection=false
        game.houses.forEach {
            if (it == gameHouse) {
            } else {
                this.lines.forEach { line ->
                    if (it.rectangle.rect.contains(line.p2)) {
                        intersection = true
                    }
                }
            }


        }
return intersection
    }
    fun isIntersectOtherSources(): Boolean {
        var intersection=false
        game.sources.forEach {
            if (it == source) {
            } else {
                this.lines.forEach { line ->
                    if (it.shape.rect.contains(line.p2)) {
                        intersection = true
                    }
                }
            }


        }
        return intersection
    }

}