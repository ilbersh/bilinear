package bilinear
import chisel3._

import chisel3.core.FixedPoint
class Pixel(xval: FixedPoint, yval: FixedPoint) {
    val x = xval
    val y = yval
    
    def + (that: Pixel): Pixel =
        new Pixel(
            x + that.x,
            y + that.y
        )
    
    def * (that: FixedPoint): Pixel =
        new Pixel(
            x * that,
            y * that
        )
    
    def inTile (mx: Pixel): Bool =
        (x < mx.x) || (y < mx.y)
}

class OffregWrapper extends Module {
    val io = IO(new Bundle{})
    val start_x = 25.F(20.BP);
    val start_y = 41.F(20.BP);
    val del_x = 8.F(20.BP);
    val del_y = 3.F(20.BP);
    
    val mio = PixelOffreg(start_x, start_y, del_x, del_y)
    
    val myUInt = mio.offset_x(0).asSInt()
    printf(p"Hello myUInt = $myUInt")
    
    val myUInt1 = mio.offset_x(1).asSInt()
    printf(p"Hello myUInt = $myUInt1")
    
    val myUInt2 = mio.offset_x(2).asSInt()
    printf(p"Hello myUInt = $myUInt2")
    
    val myUInt3 = mio.offset_y(6).asSInt()
    printf(p"Hello myUInt = $myUInt3")
    val myUInt4 = mio.vld(5)
    printf(p"Hello myUInt = $myUInt4")
}

import chisel3.core.FixedPoint
class PixelOffreg extends Module {
    val io = IO(new Bundle {
        val start_x = Input(FixedPoint(32.W, 20.BP))
        val start_y = Input(FixedPoint(32.W, 20.BP))
        val del_x = Input(FixedPoint(32.W, 30.BP))
        val del_y = Input(FixedPoint(32.W, 30.BP))
        val offset_x = Output(Vec(16, FixedPoint(32.W, 20.BP)))
        val offset_y = Output(Vec(16, FixedPoint(32.W, 20.BP)))
        val vld = Output(Vec(16, Bool())) 
    })
    
    val start = new Pixel(io.start_x, io.start_y)
    val del = new Pixel(io.del_x, io.del_y)

    val ran = Seq(0.U, 1.U, 2.U,  3.U,  4.U,  5.U,  6.U,  7.U,
                  8.U, 9.U, 10.U, 11.U, 12.U, 13.U, 14.U, 15.U
        ).map(i => (i << 20).asFixedPoint(20.BP))
    
    val offset = ran.map(i => (start + del*i))
    
    io.offset_x := offset.map(i => i.x)
    io.offset_y := offset.map(i => i.y)
    
    val mx = new Pixel(16.F(20.BP), 6.F(20.BP))
    io.vld := offset.map(i => i.inTile(mx))
}

object PixelOffreg {
    def apply(start_x: FixedPoint,
              start_y: FixedPoint,
              del_x: FixedPoint,
              del_y: FixedPoint) = {
        val m = Module(new PixelOffreg)
        m.io.start_x := start_x
        m.io.start_y := start_y
        m.io.del_x := del_x
        m.io.del_y := del_y
        m.io
    }
}

object OffregWrapper extends App {
  chisel3.Driver.execute(args, () => new OffregWrapper)
}
