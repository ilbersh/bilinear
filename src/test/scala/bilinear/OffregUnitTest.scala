// See README.md for license details.

package bilinear

import java.io.File

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}


class OffregUnitTester(c: PixelOffregFold) extends PeekPokeTester(c) {
  def mulFixed(l: Int, r: Int): Int = {
    ((l >> 4) * r) >> 6
  }
  def computePixelOffregFold(xstart: Int, ystart: Int, xdel: Int, ydel: Int): (Int, Int, Int) = {
    val xst = xstart
    val yst = ystart
    val dx = xdel
    val dy = ydel
    var xoff: Long = 0
    var yoff: Long = 0
    var vld = 0
    for(i <- 0 until 16) {
        val px = xst + mulFixed(dx,i)
        val py = yst + mulFixed(dy,i)
        xoff ^= px
        yoff ^= py
        if (px < (16 << 20)+xst && py < (6 << 20)+yst)
            vld |= 1 << i
    }
    (xoff, yoff, vld)
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
    printf("start_x = %6.5f\n", start_x.toFloat/Math.pow(2,20))
    printf("start_y = %6.5f\n", start_y.toFloat/Math.pow(2,20))
    printf("del_x = %6.5f\n", del_x.toFloat/Math.pow(2,30))
    printf("del_y = %6.5f\n", del_y.toFloat/Math.pow(2,30))
    poke(bilinear.io.start_x, start_x)
    poke(bilinear.io.start_y, start_y)
    poke(bilinear.io.del_x, del_x)
    poke(bilinear.io.del_y, del_y)
    
    step(1)

    val (xoff, yoff, vld) = computePixelOffregFold(start_x, start_y, del_x, del_y)
    
    printf("xoff = %6.5f\n", xoff.toFloat/Math.pow(2,20))
    printf("yoff = %6.5f\n", yoff.toFloat/Math.pow(2,20))
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
