// See README.md for license details.

package bilinear

import java.io.File

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}


class OffregUnitTester(c: PixelOffregFold) extends PeekPokeTester(c) {
  def fixedToFloat(v: Int, frac: Int): Float = {
    v.toFloat/Math.pow(2,frac).toFloat
  }
  def floatToFixed(v: Float, frac: Int): Int = {
    (v*Math.pow(2,frac)).toInt
  }
  def mulFixed(l: Int, r: Int): Int = {
    ((l >> 4) * r) >> 6
  }
  def computePixelOffregFold(xstart: Int, ystart: Int, xdel: Int, ydel: Int): (Int, Int) = {
    var xoff: Long = 0
    var yoff: Long = 0
    for(i <- 0 until 16) {
        val px = xstart + mulFixed(xdel,i)
        val py = ystart + mulFixed(ydel,i)
        xoff ^= px
        yoff ^= py
    }
    (xoff, yoff)
  }
  def computePixelVLD(xstart: Float, ystart: Float, xdel: Float, ydel: Float): Int = {
    var xoff = 0
    var yoff = 0
    var vld = 0
    for(i <- 0 until 16) {
        val px = xstart + xdel*i
        val py = ystart + ydel*i
        //printf("px(%d) = %6.5f\n", i, px)
        //printf("py(%d) = %6.5f\n", i, py)
        if (px < 16.0 && py < 6.0)
            vld |= 1 << i
    }
    vld
  }

  private val bilinear = c
  ////
  val maxsize = 2048 << 20 - 1 // 2048
  val max_del = 2136746230 // 1.99
  for(i <- 1 until 500) {
    val start_x = rnd.nextInt(maxsize)
    val start_y = rnd.nextInt(maxsize)
    val del_x = rnd.nextInt(max_del)*2 - 2136746230
    val del_y = rnd.nextInt(max_del)*2 - 2136746230
    val start_x_flt = fixedToFloat(start_x, 20)
    val start_y_flt = fixedToFloat(start_y, 20)
    val del_x_flt = fixedToFloat(del_x, 30)
    val del_y_flt = fixedToFloat(del_y, 30)
    printf("start_x = %6.5f\n", start_x_flt)
    printf("start_y = %6.5f\n", start_y_flt)
    printf("del_x = %6.5f\n", del_x_flt)
    printf("del_y = %6.5f\n", del_y_flt)
    poke(bilinear.io.start_x, start_x)
    poke(bilinear.io.start_y, start_y)
    poke(bilinear.io.del_x, del_x)
    poke(bilinear.io.del_y, del_y)
    
    step(1)

    val (xoff, yoff) = computePixelOffregFold(start_x, start_y, del_x, del_y)
    val vld = computePixelVLD(start_x_flt, start_y_flt, del_x_flt, del_y_flt)
    
    //printf("xoff = %d\n", xoff)
    //printf("yoff = %d\n", yoff)
    printf("vld = %s\n", vld.toBinaryString)
    
    expect(bilinear.io.of_x_fold, xoff)
    expect(bilinear.io.of_y_fold, yoff)
    expect(bilinear.io.vld_fold, vld)
  }
}

class OffregToMemTester(c: OffregToMem) extends PeekPokeTester(c) {
	
  private val bilinear = c
  ////
  val maxsize = 2048 << 20 - 1 // 2048
  val max_del = 2136746230 // 1.99
    val start_x = rnd.nextInt(maxsize)
    val start_y = rnd.nextInt(maxsize)
    val del_x = rnd.nextInt(max_del)*2 - 2136746230
    val del_y = rnd.nextInt(max_del)*2 - 2136746230
    poke(bilinear.io.start_x, start_x)
    poke(bilinear.io.start_y, start_y)
    poke(bilinear.io.del_x, del_x)
    poke(bilinear.io.del_y, del_y)
    poke(bilinear.io.wen,    1)
    poke(bilinear.io.waddr, 0)//
	
    step(1)
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly bilinear.OffregTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly bilinear.OffregTester'
  * }}}
  */
  
  /*
class OffregTester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(false && firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "Offreg" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new Offreg, backendName) {
        c => new OffregUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new Offreg) {
      c => new OffregUnitTester(c)
    } should be (true)
  }

  "using --backend-name verilator" should "be an alternative way to run using verilator" in {
    if(backendNames.contains("verilator")) {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new Offreg) {
        c => new OffregUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new Offreg) {
      c => new OffregUnitTester(c)
    } should be(true)
  }


  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new Offreg
    ) {

      c => new OffregUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/make_a_vcd.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new Offreg
    ) {

      c => new OffregUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}
*/
