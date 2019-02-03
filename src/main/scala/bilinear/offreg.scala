package bilinear
import chisel3._

import chisel3.core.FixedPoint
// Main Module.
// Solve 32-bit Pixel Offsets in Tile 16x6
class PixelOffreg extends Module {
    val io = IO(new Bundle {
        val start_x = Input(SInt(32.W))
        val start_y = Input(SInt(32.W))
        val del_x = Input(SInt(32.W))
        val del_y = Input(SInt(32.W))
        val offset_x = Output(Vec(16, SInt(32.W)))
        val offset_y = Output(Vec(16, SInt(32.W)))
        val vld = Output(UInt(32.W))
    })
    
    val start = new Pixel(io.start_x.asFixedPoint(20.BP), io.start_y.asFixedPoint(20.BP))
    val del = new Pixel(io.del_x.asFixedPoint(30.BP), io.del_y.asFixedPoint(30.BP))

    val ran = Seq(0.S, 1.S, 2.S,  3.S,  4.S,  5.S,  6.S,  7.S,
                  8.S, 9.S, 10.S, 11.S, 12.S, 13.S, 14.S, 15.S
        )
    
    val offset = ran.map(i => (start + del.scale(i)))
    
    io.offset_x := offset.map(i => i.x.asSInt())
    io.offset_y := offset.map(i => i.y.asSInt())
    
    val mx = new Pixel(16.F(20.BP), 6.F(20.BP))
    val vld = offset.map(i => i.inTile(mx))
    io.vld := vld.indices.map(i => vld(i).asUInt() << i).foldLeft(0.U)(_ | _)
}

object PixelOffreg {
    // Using module as function
    def apply(start_x: SInt,
              start_y: SInt,
              del_x: SInt,
              del_y: SInt) = {
        val m = Module(new PixelOffreg)
        m.io.start_x := start_x
        m.io.start_y := start_y
        m.io.del_x := del_x
        m.io.del_y := del_y
        m.io
    }
}


import chisel3.core.FixedPoint
// Advanced class need for easier work with (x, y) coordinate
class Pixel(xval: FixedPoint, yval: FixedPoint) {
    val x = xval
    val y = yval
    
    def + (that: Pixel): Pixel =
        new Pixel(
            x + that.x,
            y + that.y
        )
 
    def scale (that: SInt): Pixel =
        new Pixel(
            ((x.asSInt() >> 4) * that).asFixedPoint(20.BP) >> 6,
            ((y.asSInt() >> 4) * that).asFixedPoint(20.BP) >> 6
        )

    def inTile (mx: Pixel): Bool =
        (x < mx.x) && (y < mx.y)
}

// Testing module. XOR results
class PixelOffregFold extends Module {
    val io = IO(new Bundle {
        val start_x = Input(SInt(32.W))
        val start_y = Input(SInt(32.W))
        val del_x = Input(SInt(32.W))
        val del_y = Input(SInt(32.W))
        val of_x_fold = Output(SInt(32.W))
        val of_y_fold = Output(SInt(32.W))
        val vld_fold = Output(UInt(32.W))
    })
    
    val mio = PixelOffreg(io.start_x, io.start_y, io.del_x, io.del_y)
    
    io.of_x_fold := mio.offset_x.foldLeft(0.S)(_ ^ _)
    io.of_y_fold := mio.offset_y.foldLeft(0.S)(_ ^ _)
    io.vld_fold := mio.vld
}


object PixelOffregFold {
    // Using module as function
    def apply(start_x: SInt,
              start_y: SInt,
              del_x: SInt,
              del_y: SInt) = {
        val m = Module(new PixelOffregFold)
        m.io.start_x := start_x
        m.io.start_y := start_y
        m.io.del_x := del_x
        m.io.del_y := del_y
        m.io
    }
}

// Memory testing module
class OffregToMem extends Module {
    val io = IO(new Bundle {
        val start_x = Input(SInt(32.W))
        val start_y = Input(SInt(32.W))
        val del_x = Input(SInt(32.W))
        val del_y = Input(SInt(32.W))
        val raddr = Input(UInt(32.W))
        val waddr = Input(UInt(32.W))
        val ren = Input(Bool())
        val wen = Input(Bool())
    }) 
    val mio = PixelOffreg(io.start_x, io.start_y, io.del_x, io.del_y)
    
    val writeAddr = io.waddr

    val of_mem = Mem(33, UInt(32.W))
    when (io.wen) {
        mio.offset_x.foreach(i => {
            of_mem(writeAddr+i.asUInt()*4.U) := mio.offset_x(i.asUInt()).asUInt()
            of_mem(writeAddr+i.asUInt()*4.U+4.U) := mio.offset_y(i.asUInt()).asUInt()
        })
        of_mem(writeAddr+32.U) := mio.vld
    }
    
}

// Generating Verilog. Run:
// runMain bilinear.OffregWrapper
// result will be located in ./PixelOffreg.v
object OffregWrapper extends App {
  chisel3.Driver.execute(args, () => new PixelOffreg)
}
